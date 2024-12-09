package org.fyp.api;

import org.fyp.cli.Streak;
import org.fyp.client.FailedToGetStreakException;
import org.fyp.client.FailedToUpdateStreakException;
import org.fyp.db.StreakDao;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

public class StreakService {
    private StreakDao streakDao;

    public StreakService(StreakDao streakDao) {
        this.streakDao = streakDao;
    }

    public void updateStreak(int userId, Date lastActivityDate) throws FailedToUpdateStreakException, SQLException {
        // Get the last streak date from the database (returns LocalDate)
        LocalDate lastStreakDate = streakDao.getLastActivityDate(userId);
        LocalDate startOfCurrentWeek = getStartOfWeek(lastActivityDate);

        if (lastStreakDate == null || lastStreakDate.isBefore(startOfCurrentWeek)) {
            // New week, reset streak if necessary
            int newStreak = (lastStreakDate == null || !lastStreakDate.isEqual(startOfCurrentWeek)) ? 1 : 0;
            // Convert LocalDate to java.sql.Date for the database
            Date sqlStartOfCurrentWeek = Date.valueOf(startOfCurrentWeek);
            streakDao.updateStreak(userId, newStreak, sqlStartOfCurrentWeek);
        } else if (lastStreakDate.isEqual(startOfCurrentWeek)) {
            // User has already read a book this week, do nothing
            return;
        } else {
            // If the streak is broken (i.e., lastStreakDate is older than start of the current week)
            streakDao.resetStreak(userId);
        }
    }

    private LocalDate getStartOfWeek(Date date) {
        // Convert java.sql.Date to LocalDate
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // Set the calendar to the first day of the week (Monday)
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        LocalDate startOfWeek = calendar.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        return startOfWeek;
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
