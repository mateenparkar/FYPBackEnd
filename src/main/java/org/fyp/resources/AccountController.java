package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.AccountService;
import org.fyp.cli.UpdateRequest;
import org.fyp.client.FailedToUpdatePasswordException;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Account API")
@Path("/api")
public class AccountController {
    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PUT
    @Path("/account")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePassword(UpdateRequest updateRequest) {
        try{
            accountService.updatePassword(updateRequest);
            return Response.status(Response.Status.OK).build();
        }catch(FailedToUpdatePasswordException e){
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }catch(SQLException e){
            return Response.serverError().build();
        }
    }

}
