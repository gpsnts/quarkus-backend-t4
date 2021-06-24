package br.unisinos.arquitetura.t4.controller;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import br.unisinos.arquitetura.t4.entity.Estoque;
import br.unisinos.arquitetura.t4.entity.Produto;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("estoque")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class EstoqueController {

		private static final Logger LOGGER = Logger.getLogger(EstoqueController.class.getName());

		@GET
		public List<Estoque> get() {
				return Estoque.listAll();
		}

		@GET
		@Path("{id}")
		public Estoque getSingle(@PathParam Long id) {

				Estoque entity = Estoque.findById(id);

				if (entity == null) {
						throw new WebApplicationException("Estoque with id of " + id + " does not exist.", 404);
				}

				Produto produto = Produto.findById(entity.getProduto().getId());

				if (produto == null) {
						throw new WebApplicationException("Produto with id of " + entity.getProduto().getId() + " does not exist.",
										404);
				}

				return entity;
		}

		@POST
		@Transactional
		public Response create(Estoque estoque) {

				if (estoque.id != null) {
						throw new WebApplicationException("Id was invalidly set on request.", 422);
				}

				Produto produto = Produto.findById(estoque.getProduto().getId());

				if (produto == null) {
						throw new WebApplicationException("Produto inválido.", 422);
				}
				String sku = "sku-" + String.valueOf(hashCode());

				estoque.setSku(sku);
				estoque.setProduto(produto);
				estoque.persist();

				return Response.ok(estoque).status(201).build();
		}

		@DELETE
		@Path("{id}")
		@Transactional
		public Response detSingle(@PathParam Long id) {

				Estoque entity = Estoque.findById(id);

				if (entity == null) {
						throw new WebApplicationException("Produto não encontrado no estoque.", 404);
				}

				Estoque.deleteById(id);

				return Response.ok(entity).status(200).build();
		}
}
