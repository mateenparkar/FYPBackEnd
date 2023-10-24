package org.fyp.resources;


import io.swagger.annotations.Api;
import org.fyp.api.AuthService;
import org.fyp.cli.LoginRequest;
import org.fyp.cli.LoginResponse;

import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Api("Authentication APi")
@Path("/api")
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)

    public Response login(LoginRequest login){
        try{
            String response = authService.login(login);

            LoginResponse loginResponse = new LoginResponse(response);

            return Response.ok(loginResponse).build();
        }catch(Exception e){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
}
