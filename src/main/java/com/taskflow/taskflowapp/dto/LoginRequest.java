package com.taskflow.taskflowapp.dto;

import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

// TODO add in the future RegisterRequest
public class LoginRequest {
    @NotNull
    @Size(min = 4, max = 20)
    private String username;

    @NotNull
    @Size(min = 5, max = 20)
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

