package org.fyp.resources;


import io.swagger.annotations.Api;
import org.fyp.api.AuthService;
import org.fyp.cli.LoginRequest;
import org.fyp.cli.LoginResponse;
import org.fyp.cli.RegisterRequest;
import org.fyp.client.FailedLoginException;
import org.fyp.client.FailedToRegisterException;

import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

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
        }catch(FailedLoginException e){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @POST
    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequest registerRequest){
        try {
            authService.register(registerRequest);

            return Response.status(Response.Status.CREATED).build();
        } catch (FailedToRegisterException e) {

            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }catch(SQLException e){
            return Response.serverError().build();
        }
    }
}
