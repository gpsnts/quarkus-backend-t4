package br.unisinos.arquitetura.t4;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello-resteasy")
public class GreetingResource {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String hello() {
		return "Hello RESTEasy";
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/v1")
	public String hello_v1() {
		return "Hello RESTEasy V1";
	}
}