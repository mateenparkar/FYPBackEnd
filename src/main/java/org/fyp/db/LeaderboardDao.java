package org.fyp.db;

import io.swagger.models.auth.In;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderboardDao {
    private DatabaseConnector databaseConnector;

    public LeaderboardDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public Map<Integer, Integer> getLeaderboard() throws SQLException {
        Map<Integer, Integer> userReadCount = new HashMap<>();
        Connection c = databaseConnector.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT user_id, COUNT(*) AS read_count " +
                "FROM ReadBooks " +
                "WHERE read_status = 'Read' " +
                "GROUP BY user_id ");


        while(rs.next()){
            int userId = rs.getInt("user_id");
            int readCount = rs.getInt("read_count");
            userReadCount.put(userId, readCount);
        }
        return userReadCount;
    }

}
