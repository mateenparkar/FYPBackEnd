package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.StreakService;
import org.fyp.cli.Streak;
import org.fyp.client.FailedToGetStreakException;
import org.fyp.client.FailedToUpdateStreakException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.sql.SQLException;

@Api("Streak API")
@Path("/api")
public class StreakController {
    private StreakService streakService;

    public StreakController(StreakService streakService) {
        this.streakService = streakService;
    }

    @POST
    @Path("/updateStreak/{userId}/{lastActivityDate}")
    @Produces(MediaType.APPLICATION_JSON)
    public void updateStreak(@PathParam("userId") int userId,
                             @PathParam("lastActivityDate") Date lastActivityDate)
            throws FailedToUpdateStreakException, SQLException {
        try {
            streakService.updateStreak(userId, lastActivityDate);
        } catch (SQLException e) {
            throw new FailedToUpdateStreakException();
        }
    }

    @PUT
    @Path("/resetStreak/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void resetStreak(@PathParam("userId") int userId) throws SQLException, FailedToUpdateStreakException {
        try {
            streakService.resetStreak(userId);
        } catch (SQLException e) {
            throw new FailedToUpdateStreakException();
        }
    }

    @GET
    @Path("/getStreak/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Streak getStreak(@PathParam("userId") int userId) throws SQLException, FailedToGetStreakException {
        try{
            return streakService.getStreak(userId);
        }catch (SQLException | FailedToGetStreakException e){
            throw new FailedToGetStreakException();
        }
    }
}
