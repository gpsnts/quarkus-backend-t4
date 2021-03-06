package br.unisinos.arquitetura.t4.controller;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.unisinos.arquitetura.t4.dto.Message;
import io.quarkus.vertx.web.Header;

@Path("/resource")
public class RolesExampleController {
	@RolesAllowed("USER")
	@GET
	@Path("/user")
	@Produces(MediaType.APPLICATION_JSON)
	public Response user(@HeaderParam("Authorization") String authorization) {
		return Response.ok(new Message("Content for user")).build();
	}
	
	@RolesAllowed("ADMIN")
	@GET
	@Path("/admin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response admin(@HeaderParam("Authorization") String authorization) {
		return Response.ok(new Message("Content for admin")).build();
	}
	
	@RolesAllowed({"USER", "ADMIN"})
	@GET @Path("/user-or-admin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response userOrAdmin(@HeaderParam("Authorization") String authorization) {
		return Response.ok(new Message("Content for user or admin")).build();
	}
}
