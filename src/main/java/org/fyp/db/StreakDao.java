package org.fyp.db;

import org.fyp.cli.Streak;

import java.sql.*;
import java.time.LocalDate;

public class StreakDao {
    private DatabaseConnector databaseConnector;

    public StreakDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void updateStreak(int userId, int newStreak, Date lastActivityDate) throws SQLException {
        String query = "INSERT INTO streaks (user_id, current_streak, last_activity_date) " +
                "VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE current_streak = ?, last_activity_date = ?";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, newStreak);
            stmt.setDate(3, lastActivityDate);
            stmt.setInt(4, newStreak);
            stmt.setDate(5, lastActivityDate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle logging or rethrow exception based on your needs
            throw e;
        }
    }

    public void resetStreak(int userId) throws SQLException {
        String query = "UPDATE streaks SET current_streak = 0, last_activity_date = NULL WHERE user_id = ?";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle logging or rethrow exception based on your needs
            throw e;
        }
    }

    public LocalDate getLastActivityDate(int userId) throws SQLException {
        String query = "SELECT last_activity_date FROM streaks WHERE user_id = ?";
        try (Connection connection = databaseConnector.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDate("last_activity_date").toLocalDate();
                }
            }
        }
        return null; // Return null if no streak exists
    }

    public Streak getStreak(int userId) throws SQLException {
        Connection c = databaseConnector.getConnection();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery("SELECT current_streak, last_activity_date FROM streaks "
                + "WHERE user_id = " + userId);

        if(rs.next()){
            Streak streak = new Streak(
                    userId,
                    rs.getInt("current_streak"),
                    rs.getDate("last_activity_date")
            );
            return streak;
        }
        return null;
    }
}
