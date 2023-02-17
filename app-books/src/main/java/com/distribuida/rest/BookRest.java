package com.distribuida.rest;

import com.distribuida.db.Book;
import com.distribuida.servicios.BookService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@Path("/books")
public class BookRest {
    @Inject
    private BookService bookService;

    //OBTENER TODOS
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtiene libros")
    @APIResponse(responseCode = "200", description = "Obtiene todos los libros", content =
    @Content(mediaType = "application/json", schema = @Schema(allOf = Book.class)))
    public List<Book> findAll() {
        return bookService.getBooks();
    }

    //OBTENER POR ID
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtiene libros por Id")
    @APIResponse(responseCode = "200", description = "Obtiene libro por Id", content =
    @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)))
    @APIResponse(responseCode = "400", description = "Libro por Id")
    public Book findById(@Parameter(description = "ID BOOK", required = true) @PathParam("id") Integer id)  {
        return bookService.getBookById(id);
    }

    //ELIMINAR
    @DELETE
    @Path("/{id}")
    @Operation(description = "Eliminar")
    @APIResponse(responseCode = "204", description = "El libro se ha eliminado")
    @APIResponse(responseCode = "500", description = "Existe problemas con el servidor")
    public Response delete(
            @Parameter(description = "ID BOOK", required = true)
            @PathParam("id") Integer id) {
        bookService.delete(id);
        return Response.status((Response.Status.NO_CONTENT)).build();
    }
    //INSERTAR
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(description = "Insertar")
    @APIResponse(responseCode = "201", description = "El libro se creo con exito")
    @APIResponse(responseCode = "500", description = "xiste problemas con el servidor")
    public Response create(
            @RequestBody(description = "Se agrega el libro", required = true,
                    content = @Content(schema = @Schema(implementation = Book.class)))
            Book b){
        bookService.creteBook(b);
        return Response.status(Response.Status.CREATED).build();
    }
    //ACTUALIZAR
    @PUT
    @Path("/{id}")
    @Operation(description = "Actualizar")
    @APIResponse(responseCode = "200", description = "Actualizacion correcta")
    @APIResponse(responseCode = "500", description = "Actualizacion presenta problemas")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(
            @RequestBody(description = "Datos del libro para actualizar", required = true,
                    content = @Content(schema = @Schema(implementation = Book.class)))
            Book b,
            @Parameter(description = "Id Book", required = true)
            @PathParam("id") Integer id){
        bookService.updateBook(id, b);
        return Response.status((Response.Status.OK)).build();
    }

}
