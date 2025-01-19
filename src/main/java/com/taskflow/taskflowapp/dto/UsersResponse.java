package com.taskflow.taskflowapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UsersResponse {
    private Long id;
    private String username;
}