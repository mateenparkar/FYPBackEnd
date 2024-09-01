package org.fyp.client;

public class FailedToGetLeaderboardException extends Throwable{
    @Override
    public String getMessage() {
        return "Failed to get leaderboard";
    }
}
