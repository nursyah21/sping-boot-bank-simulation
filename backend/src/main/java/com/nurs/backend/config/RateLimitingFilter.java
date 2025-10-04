package com.nurs.backend.config;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Component
@RequiredArgsConstructor
public class RateLimitingFilter extends OncePerRequestFilter {
  private final RateLimiterProperties properties;
  private final Map<String, Map<String, AtomicLong>> requestCounts = new ConcurrentHashMap<>();
  private static final String LOGIN_PATH = "/auth/login";

  @Override
  protected void doFilterInternal( 
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    
    if (request.getRequestURI().endsWith(LOGIN_PATH) && request.getMethod().equals("POST")) {
      val clientIp = getClientIP(request);
      val currentTime = System.currentTimeMillis();

      requestCounts.putIfAbsent(clientIp, createNewEntry());
      val entry = requestCounts.get(clientIp);

      if (currentTime - entry.get("timestamp").get() > properties.getTimeWindowMs()) {
          entry.get("count").set(1);
          entry.get("timestamp").set(currentTime);
      } else {
          entry.get("count").incrementAndGet();
      }

      if (entry.get("count").get() > properties.getMaxRequests()) {
          response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
          response.getWriter().write("{\"message\":\"Too many login attempts. Please try again later.\"}");
          response.setContentType("application/json");
          return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private Map<String, AtomicLong> createNewEntry() {
    val map = new ConcurrentHashMap<String, AtomicLong>();
    map.put("count", new AtomicLong(0));
    map.put("timestamp", new AtomicLong(System.currentTimeMillis()));
    return map;
  }

  private String getClientIP(HttpServletRequest request) {
    val xfHeader = request.getHeader("X-Forwarded-For");
    if (xfHeader == null || xfHeader.isEmpty() || !xfHeader.contains(".")) {
        return request.getRemoteAddr();
    }
    return xfHeader.split(",")[0];
  }
}
