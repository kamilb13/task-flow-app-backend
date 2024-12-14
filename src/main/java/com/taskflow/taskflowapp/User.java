package com.taskflow.taskflowapp;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //@Column(unique = true)
    private String username;
    private String password;
    //private Set<Role> roles;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
