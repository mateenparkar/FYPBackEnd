package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.BookService;
import org.fyp.client.FailedToGetBooksException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Books API")
@Path("/api")
public class BooksController {
    private BookService bookService;

    public BooksController(BookService bookService) {
        this.bookService = bookService;
    }


    @GET
    @Path("/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks(){
        try{
            return Response.ok(bookService.getAllBooks()).build();
        }catch(FailedToGetBooksException e){
            return Response.serverError().build();

        }
    }

    @GET
    @Path("/books/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookById(@PathParam("id") int id){
        try{
            return Response.ok(bookService.getBookById(id)).build();
        }catch(FailedToGetBooksException e){
            return Response.serverError().build();

        }
    }


}
