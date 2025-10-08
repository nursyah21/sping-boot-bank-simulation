package org.acme.controller;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/health")
public class HealthController {
    
    @GET
    @Produces(value = MediaType.TEXT_PLAIN)
    public String getHealth(){
        return "ok";
    }
}
