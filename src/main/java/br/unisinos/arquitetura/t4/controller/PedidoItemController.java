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

import br.unisinos.arquitetura.t4.entity.Estoque;
import br.unisinos.arquitetura.t4.entity.Pedido;
import br.unisinos.arquitetura.t4.entity.PedidoItem;
import br.unisinos.arquitetura.t4.entity.Produto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.quarkus.panache.common.Sort;

@Path("pedido_item")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class PedidoItemController {

    private static final Logger LOGGER = Logger.getLogger(PedidoItemController.class.getName());

    @GET
    public List<PedidoItem> get() {
        return PedidoItem.listAll();
    }

    @GET
    @Path("{id}")
    public PedidoItem getSingle(@PathParam Long id) {

        PedidoItem entity = PedidoItem.findById(id);

        if (entity == null) {
            throw new WebApplicationException("PedidoItem with id of " + id + " does not exist.", 404);
        }

        Pedido pedido = Pedido.findById(entity.getPedido().getId());

        if (pedido == null) {
            throw new WebApplicationException("Pedido inválido.", 422);
        }

        Estoque estoque = Estoque.findById(entity.getEstoque().getId());

        if (estoque == null) {
            throw new WebApplicationException("Item de estoque inválido.", 422);
        }

        entity.setPedido(pedido);
        entity.setEstoque(estoque);

        return entity;
    }

    @POST
    @Transactional
    public Response create(PedidoItem pedido_item) {

        if (pedido_item.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        Pedido pedido = Pedido.findById(pedido_item.getPedido().getId());

        if (pedido == null) {
            throw new WebApplicationException("Pedido inválido.", 422);
        }

        Estoque estoque = Estoque.findById(pedido_item.getEstoque().getId());

        if (estoque == null) {
            throw new WebApplicationException("Item de estoque inválido.", 422);
        }

        pedido_item.setPedido(pedido);
        pedido_item.setEstoque(estoque);
        pedido_item.persist();

        return Response.ok(pedido_item).status(201).build();
    }
}
