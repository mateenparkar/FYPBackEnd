package org.fyp.cli;

import java.sql.Date;
import java.time.LocalDate;

public class Streak {
    private int userId;

    public Streak(int userId, int currentStreak, Date lastActivityDate) {
        this.userId = userId;
        this.currentStreak = currentStreak;
        this.lastActivityDate = lastActivityDate;
    }

    private int currentStreak;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public void setCurrentStreak(int currentStreak) {
        this.currentStreak = currentStreak;
    }

    public Date getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(Date lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }

    private Date lastActivityDate;


}
