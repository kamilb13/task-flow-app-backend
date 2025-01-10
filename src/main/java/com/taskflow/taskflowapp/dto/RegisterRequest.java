package com.taskflow.taskflowapp.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequest {
    @NotNull
    @Size(min = 4, max = 20)
    private String username;

    @NotNull
    @Size(min = 5, max = 20)
    private String password;

    @NotNull
    @Size(min = 5, max = 30)
    private String email;
}
