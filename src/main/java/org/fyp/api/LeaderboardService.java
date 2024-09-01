package org.fyp.api;

import org.fyp.client.FailedToGetLeaderboardException;
import org.fyp.db.LeaderboardDao;

import java.sql.SQLException;
import java.util.Map;

public class LeaderboardService {
    private LeaderboardDao leaderboardDao;

    public LeaderboardService(LeaderboardDao leaderboardDao) {
        this.leaderboardDao = leaderboardDao;
    }

    public Map<Integer, Integer> getLeaderboard() throws FailedToGetLeaderboardException {
        try {
            return leaderboardDao.getLeaderboard();
        } catch (SQLException e) {
            throw new FailedToGetLeaderboardException();
        }
    }
}
