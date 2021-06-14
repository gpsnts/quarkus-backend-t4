package br.unisinos.arquitetura.t4.controller;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.unisinos.arquitetura.t4.dto.request.AuthRequest;
import br.unisinos.arquitetura.t4.dto.response.AuthResponse;
import br.unisinos.arquitetura.t4.entity.User;
import br.unisinos.arquitetura.t4.repository.UserRepository;
import br.unisinos.arquitetura.t4.security.EncodingHandler;
import br.unisinos.arquitetura.t4.security.TokenHandler;

@Path("/auth")
public class AuthController {
	@Inject
	EncodingHandler encodingHandler;

	@Inject
	UserRepository userRepository;

	@ConfigProperty(name = "jwt.duration") public Long duration;
	@ConfigProperty(name = "jwt.issuer") public String issuer;

	@PermitAll
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(AuthRequest req) {
		User user = userRepository.findByUsername(req.getPassword());

		boolean existingUser = user.getPassword().equals(
			encodingHandler.encode(req.getPassword())
		);

		if (user != null && existingUser) {
			try {
				return Response.ok(
					new AuthResponse(
						TokenHandler.generateToken(user.getUsername(), user.getRoles(), duration, issuer)
					)
				).build();
			} catch (Exception e) {
				return Response.status(Status.UNAUTHORIZED).build();
			}
		}
		else {
			return Response.status(Status.NOT_FOUND).build();
		}
	}
}
