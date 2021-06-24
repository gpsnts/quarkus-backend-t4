package br.unisinos.arquitetura.t4.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import br.unisinos.arquitetura.t4.entity.Categoria;
import br.unisinos.arquitetura.t4.entity.Produto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.panache.common.Sort;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("produto")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class ProdutoController {

		private static final Logger LOGGER = Logger.getLogger(ProdutoController.class.getName());

		@GET
		public List<Produto> get() {
				return Produto.listAll(Sort.by("name"));
		}

		@GET
		@Path("{id}")
		public Produto getSingle(@PathParam Long id) {

				Produto entity = Produto.findById(id);

				if (entity == null) {
						throw new WebApplicationException("Produto with id of " + id + " does not exist.", 404);
				}

				return entity;
		}

		@POST
		@Transactional
		public Response create(Produto produto) {

				if (produto.id != null) {
						throw new WebApplicationException("Id was invalidly set on request.", 422);
				}

				Set<Categoria> categorias_body = produto.getCategorias();

				produto.setCategorias(new HashSet<>());

				for (Categoria categoria_aux : categorias_body) {

						Categoria categoria = Categoria.findById(categoria_aux.getId());

						if (categoria == null) {
								continue;
						}

						produto.addCategoria(categoria);
				}

				produto.persist();
				return Response.ok(produto).status(201).build();
		}

		@PUT
		@Path("{id}")
		@Transactional
		public Response update(@PathParam Long id, Produto produto_request) {

				Produto produto = Produto.findById(id);

				if (produto == null) {
						throw new WebApplicationException("Id was invalidly set on request.", 422);
				}

				Set<Categoria> categorias_body = produto_request.getCategorias();

				produto.setCategorias(new HashSet<>());

				for (Categoria categoria_aux : categorias_body) {

						Categoria categoria = Categoria.findById(categoria_aux.getId());

						if (categoria == null) {
								continue;
						}

						produto.addCategoria(categoria);
				}

				if (!produto.isPersistent()) {
						throw new WebApplicationException("Produto inválido.", 422);
				}

				produto.setName(produto_request.getName());
				produto.setDescription(produto_request.getDescription());
				produto.setValor(produto_request.getValor());
				produto.persist();

				return Response.ok(produto).status(201).build();
		}

		@Provider
		public static class ErrorMapper implements ExceptionMapper<Exception> {

				@Inject
				ObjectMapper objectMapper;

				@Override
				public Response toResponse(Exception exception) {
						LOGGER.error("Failed to handle request", exception);

						int code = 500;
						if (exception instanceof WebApplicationException) {
								code = ((WebApplicationException) exception).getResponse().getStatus();
						}

						ObjectNode exceptionJson = objectMapper.createObjectNode();
						exceptionJson.put("exceptionType", exception.getClass().getName());
						exceptionJson.put("code", code);

						if (exception.getMessage() != null) {
								exceptionJson.put("error", exception.getMessage());
						}

						return Response.status(code).entity(exceptionJson).build();
				}
		}
}
