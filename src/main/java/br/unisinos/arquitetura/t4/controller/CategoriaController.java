package br.unisinos.arquitetura.t4.controller;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import br.unisinos.arquitetura.t4.entity.Categoria;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("categoria")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class CategoriaController {

		private static final Logger LOGGER = Logger.getLogger(CategoriaController.class.getName());

		@GET
		public List<Categoria> get() {
				return Categoria.listAll();
		}

		@GET
		@Path("{id}")
		public Categoria getSingle(@PathParam Long id) {

				Categoria entity = Categoria.findById(id);

				if (entity == null) {
						throw new WebApplicationException("Categoria with id of " + id + " does not exist.", 404);
				}

				return entity;
		}

		@POST
		@Transactional
		public Response create(Categoria categoria) {

				if (categoria.id != null) {
						throw new WebApplicationException("Id was invalidly set on request.", 422);
				}

				categoria.persist();

				return Response.ok(categoria).status(201).build();
		}
}
