package br.unisinos.arquitetura.t4.controller;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import br.unisinos.arquitetura.t4.entity.Pedido;
import br.unisinos.arquitetura.t4.entity.Pessoa;
import br.unisinos.arquitetura.t4.entity.Produto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.quarkus.panache.common.Sort;

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
