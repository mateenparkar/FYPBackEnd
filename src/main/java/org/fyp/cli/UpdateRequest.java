package org.fyp.cli;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateRequest {
    private int userId;
    private String password;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonCreator
    public UpdateRequest(
            @JsonProperty("userId") int userId,
            @JsonProperty("password") String password
    ) {
        this.userId = userId;
        this.password = password;
    }
}
