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

import br.unisinos.arquitetura.t4.entity.Pedido;
import br.unisinos.arquitetura.t4.entity.Pessoa;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("pedido")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class PedidoController {

		private static final Logger LOGGER = Logger.getLogger(PedidoController.class.getName());

		@GET
		public List<Pedido> get() {
				return Pedido.listAll();
		}

		@GET
		@Path("{id}")
		public Pedido getSingle(@PathParam Long id) {

				Pedido entity = Pedido.findById(id);

				if (entity == null) {
						throw new WebApplicationException("Pedido with id of " + id + " does not exist.", 404);
				}

				Pessoa pessoa = Pessoa.findById(entity.getPessoa().getId());

				entity.setPessoa(pessoa);

				return entity;
		}

		@POST
		@Transactional
		public Response create(Pedido pedido) {

				if (pedido.id != null) {
						throw new WebApplicationException("Id was invalidly set on request.", 422);
				}

				pedido.persist();

				return Response.ok(pedido).status(201).build();
		}
}
