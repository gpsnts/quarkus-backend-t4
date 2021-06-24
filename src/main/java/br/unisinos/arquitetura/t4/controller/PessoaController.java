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

import br.unisinos.arquitetura.t4.entity.Pessoa;
import br.unisinos.arquitetura.t4.entity.Produto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.quarkus.panache.common.Sort;

@Path("pessoa")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class PessoaController {

    private static final Logger LOGGER = Logger.getLogger(PessoaController.class.getName());

    @GET
    public List<Pessoa> get() {
        return Pessoa.listAll();
    }

    @GET
    @Path("{id}")
    public Pessoa getSingle(@PathParam Long id) {

        Pessoa entity = Pessoa.findById(id);

        if (entity == null) {
            throw new WebApplicationException("Pessoa with id of " + id + " does not exist.", 404);
        }

        return entity;
    }

    @POST
    @Transactional
    public Response create(Pessoa pessoa) {

        if (pessoa.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        pessoa.persist();

        return Response.ok(pessoa).status(201).build();
    }
}
