package org.fyp.cli;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {
    public LoginResponse(@JsonProperty("token") String token) {
        this.token = token;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }



}
