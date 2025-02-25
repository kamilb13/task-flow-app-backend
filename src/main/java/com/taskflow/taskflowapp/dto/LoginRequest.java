package com.taskflow.taskflowapp.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

// TODO add in the future RegisterRequest
@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {
    @NotNull
    @Size(min = 4, max = 20)
    private String username;

    @NotNull
    @Size(min = 4, max = 20)
    private String password;
}

