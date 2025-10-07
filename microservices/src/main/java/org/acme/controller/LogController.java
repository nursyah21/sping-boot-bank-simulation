package org.acme.controller;

import org.acme.dto.GenericResponse;
import org.acme.dto.LogRequest;
import org.acme.service.LogService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.val;

@Path("/log")
@RequiredArgsConstructor
public class LogController {
  private final LogService logService;
  
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response saveLog(@NotNull @Valid LogRequest request){
    logService.saveLog(request);
    val body = new GenericResponse<>("log success created", null);

    return Response.status(Response.Status.CREATED)
      .entity(body)
      .build();
  }
}
