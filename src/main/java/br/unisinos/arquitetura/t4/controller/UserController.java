package br.unisinos.arquitetura.t4.controller;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import br.unisinos.arquitetura.t4.dto.request.AuthRequest;
import br.unisinos.arquitetura.t4.dto.response.standard.MessageResponseBody;
import br.unisinos.arquitetura.t4.entity.Role;
import br.unisinos.arquitetura.t4.repository.UserRepository;
import br.unisinos.arquitetura.t4.utils.Validators;

@Path("/user")
public class UserController {
	@Inject UserRepository userRepository;
	
	@PermitAll
	@POST
	@Path("/create")
	public Response login(AuthRequest req) {

		if (userRepository.findByUsername(req.getUsername()) != null) return Response.status(409).entity(
			MessageResponseBody.builder()
				.message("Usuário existente")
			.build()
		).build(); 
		
		if (Validators.validateInvalidString(req.getUsername())) return Response.status(400).entity(
			MessageResponseBody.builder()
				.message("Username inválido")
			.build()	
		).build();
		
		if (Validators.validateInvalidString(req.getPassword())) return Response.status(400).entity(
			MessageResponseBody.builder()
				.message("Senha inválida")
			.build()
		).build();

		if (Validators.validPasswordLength(req.getPassword())) return Response.status(400).entity(
			MessageResponseBody.builder()
				.message("Tamanho mínimo de 8 caracteres não cumprido")
			.build()
		).build();
		
		userRepository.creatUser(
			req.getUsername(),
			req.getPassword(),
			Stream.of(Role.USER).collect(Collectors.toSet())
		);
		
		return Response.ok().build();
	}
}
