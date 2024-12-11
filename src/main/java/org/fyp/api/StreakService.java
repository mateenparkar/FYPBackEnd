package org.fyp.api;

import org.fyp.cli.Streak;
import org.fyp.client.FailedToGetStreakException;
import org.fyp.client.FailedToUpdateStreakException;
import org.fyp.db.StreakDao;

import java.sql.Date;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class StreakService {
    private StreakDao streakDao;

    public StreakService(StreakDao streakDao) {
        this.streakDao = streakDao;
    }

    public void updateStreak(int userId, Date lastActivityDate) throws FailedToUpdateStreakException, SQLException {
        // Get the current streak data from DAO
        Streak currentStreakData = streakDao.getStreak(userId);
        System.out.println("Received lastActivityDate: " + lastActivityDate);

        // Extract the last streak date and current streak from the retrieved data
        LocalDate lastStreakDate = null;
        if (currentStreakData != null && currentStreakData.getLastActivityDate() != null) {
            lastStreakDate = currentStreakData.getLastActivityDate().toLocalDate();
        }
        int currentStreak = currentStreakData != null ? currentStreakData.getCurrentStreak() : 0;

        // Convert the incoming `lastActivityDate` to LocalDate for comparison
        LocalDate activityDate = lastActivityDate.toLocalDate();

        // Calculate the start of the week for both dates
        LocalDate lastStreakWeek = lastStreakDate != null ? lastStreakDate.with(DayOfWeek.MONDAY) : null;
        LocalDate currentWeek = activityDate.with(DayOfWeek.MONDAY);

        if (lastStreakDate == null || lastStreakWeek == null || ChronoUnit.WEEKS.between(lastStreakWeek, currentWeek) > 1) {
            // Reset streak if the gap is more than one week or there is no streak
            streakDao.updateStreak(userId, 1, lastActivityDate);
        } else if (ChronoUnit.WEEKS.between(lastStreakWeek, currentWeek) == 1) {
            // Increment streak if activity happens in the following week
            streakDao.updateStreak(userId, currentStreak + 1, lastActivityDate);
        } else {
            // Do nothing if activity happens in the same week
            return;
        }
    }





    public void resetStreak(int userId) throws SQLException, FailedToUpdateStreakException {
        try {
            streakDao.resetStreak(userId);
        } catch (SQLException e) {
            throw new FailedToUpdateStreakException();
        }
    }

    public Streak getStreak(int userId) throws SQLException, FailedToGetStreakException {
        try{
            return streakDao.getStreak(userId);
        }catch(SQLException e){
            throw new FailedToGetStreakException();
        }
    }
}
