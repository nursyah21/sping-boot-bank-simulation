package org.acme.controller;

import org.acme.dto.GenericResponse;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/health")
public class HealthController {
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public GenericResponse<Void> greeting(){
        return new GenericResponse<>("ok", null);
    }
}
