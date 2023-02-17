package com.programacion.distribuida.rest;

import com.programacion.distribuida.db.Author;
import com.programacion.distribuida.repository.AuthorRepository;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@ApplicationScoped
@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorRest {

    //OBTENER AUTORES POR ID
    @GET
    @Path("/{id}")
    @Operation(summary = " Autores por Id")
    @APIResponse(responseCode = "200", description = "Se han encontrado los autores", content =
    @Content(mediaType = "application/json", schema = @Schema(implementation = Author.class)))
    @APIResponse(responseCode = "400", description = "No se han encontrado los autores")
    public Author findById(@PathParam("id") Long id) {
        return repository.findById(id);
    }

    @Inject AuthorRepository repository;

    //OBTENER TODOS
    @GET
    @Operation(summary = "Obtener todos los autores")
    @APIResponse(responseCode = "200", description = "Todos los autores", content =
    @Content(mediaType = "application/json", schema = @Schema(allOf = Author.class)))
    public List<Author> findAll() {
        return repository
                .findAll()
                .list();
    }

    //AGREGAR
    @POST
    @Operation(description = "Agregar")
    @APIResponse(responseCode = "201", description = "Se agrego correctamente")
    @APIResponse(responseCode = "500", description = "No se agrego correctamente")
    public void insert(Author obj) {
        repository.persist(obj);
    }

    //ACTUALIZAR
    @PUT
    @Path("/{id}")
    @Operation(description = "Actualizar")
    @APIResponse(responseCode = "200", description = "Se ha actualizado")
    @APIResponse(responseCode = "500", description = "No se ha actualizado")
    public void update(Author obj, @PathParam("id") Long id) {

        var author = repository.findById(id);

        author.setFirst_name(obj.getFirst_name());
        author.setLast_name(obj.getLast_name());
    }

    //ELIMINAR
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar")
    @APIResponse(responseCode = "204",description = "Se ha eliminado")
    public void delete( @PathParam("id") Long id ) {
        repository.deleteById(id);
    }
}
