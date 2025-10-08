package org.acme.controller;

import java.util.Map;

import org.acme.service.LogService;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Path("/log")
@RequiredArgsConstructor
public class LogController {
    private final LogService logService;

    @GET
    public Map<String, Object> getLogs(
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("size") @DefaultValue("10") int size
    ){
        val totalElements = logService.totalLogs();
        val totalPages = Math.ceil((double) totalElements / size);

        return Map.of(
            "content", logService.getLogsPaged(page, size),
            "page", page,
            "size", size,
            "totalElements", totalElements,
            "totalPages", totalPages
        );
    }
}
