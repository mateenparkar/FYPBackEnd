package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.GroqCloudService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
@Api("AI API")
@Path("/api")
public class GroqCloudController {
    private final GroqCloudService groqCloudService;

    public GroqCloudController(GroqCloudService groqCloudService) {
        this.groqCloudService = groqCloudService;
    }
    @POST
    @Path("/groq/{bookName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response generateQuestions(@PathParam("bookName") String bookName) {
        try {
            List<String> questions = groqCloudService.generateQuestions(bookName);
            return Response.ok(questions).build();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new IllegalArgumentException();
        }
    }
}
