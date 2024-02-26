package org.fyp.resources;


import io.dropwizard.auth.Auth;
import io.swagger.annotations.*;
import org.fyp.api.AuthService;
import org.fyp.cli.LoginRequest;
import org.fyp.cli.LoginResponse;
import org.fyp.cli.RegisterRequest;
import org.fyp.cli.User;
import org.fyp.client.FailedLoginException;
import org.fyp.client.FailedToRegisterException;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Authentication APi")
@Path("/api")
@SwaggerDefinition(
        securityDefinition = @SecurityDefinition(
                apiKeyAuthDefinitions = {
                        @ApiKeyAuthDefinition(
                                key = HttpHeaders.AUTHORIZATION,
                                name = HttpHeaders.AUTHORIZATION,
                                in = ApiKeyAuthDefinition.ApiKeyLocation.HEADER
                        )
                }
        )
)
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
        }catch(SQLException e){
            return Response.serverError().build();
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

    @GET
    @Path("/whoami")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Returns the user that is logged in", response = User.class, authorizations = {
            @Authorization(value = HttpHeaders.AUTHORIZATION)
    })
    public Response whoami(@Auth @ApiParam(hidden = true) User user) {
        return Response.ok().entity(new User(user.getUserId(), user.getUsername(), user.getEmail(), user.getHashedPassword())).build();
    }
}
