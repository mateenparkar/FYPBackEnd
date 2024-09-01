package org.fyp.resources;

import io.swagger.annotations.Api;
import org.fyp.api.LeaderboardService;
import org.fyp.client.FailedToGetLeaderboardException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api("Leaderboard API")
@Path("/api")
public class LeaderboardController {
    private LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GET
    @Path("/leaderboard")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLeaderboard() {
        try{
            return Response.ok(leaderboardService.getLeaderboard()).build();
        } catch (FailedToGetLeaderboardException e) {
            throw new RuntimeException(e);
        }
    }

}
