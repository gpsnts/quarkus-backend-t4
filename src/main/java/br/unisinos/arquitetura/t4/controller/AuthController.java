package br.unisinos.arquitetura.t4.controller;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.unisinos.arquitetura.t4.dto.response.standard.JWTResponse;
import br.unisinos.arquitetura.t4.security.JWTUtil;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import br.unisinos.arquitetura.t4.dto.request.AuthRequest;
import br.unisinos.arquitetura.t4.dto.response.AuthResponse;
import br.unisinos.arquitetura.t4.dto.response.standard.MessageResponseBody;
import br.unisinos.arquitetura.t4.entity.User;
import br.unisinos.arquitetura.t4.repository.UserRepository;
import br.unisinos.arquitetura.t4.security.EncodingHandler;
import br.unisinos.arquitetura.t4.security.TokenHandler;
import br.unisinos.arquitetura.t4.utils.ArgonHashing;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;

@Slf4j
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
		User user = userRepository.findByUsername(req.getUsername());

		if (user == null) return Response.status(404).entity(
			MessageResponseBody.builder()
				.message("Usuário não cadastrado")
			.build()
		).build();

		String inputPassword = req.getPassword();
		boolean validUser = ArgonHashing.getInstance().verify(user.getPassword(), inputPassword.toCharArray());

		if (validUser) {
			try {
				return Response.status(Status.OK).entity(
						JWTResponse.builder()
							.access_token(JWTUtil.createToken(user.getUsername(), user.getRoles(), duration, issuer))
							.exp(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(Date.from(Instant.now().plusSeconds(600L))))
						.build()
				).build();
			} catch (Exception e) {
				e.printStackTrace();
				return Response.status(Status.UNAUTHORIZED).build();
			}
		}

		else {
			return Response.status(404).entity(
				MessageResponseBody.builder()
					.message("Usuário ou senha inválidos")
				.build()
			).build();
		}
	}
}
