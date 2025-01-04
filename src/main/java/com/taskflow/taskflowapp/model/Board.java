package com.taskflow.taskflowapp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "boards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int boardCreatorId;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "user_boards",
            joinColumns = @JoinColumn(name = "board_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users = new ArrayList<>();

//    @JsonManagedReference
//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Task> toDo = new ArrayList<>();
//
//    @JsonManagedReference
//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Task> inProgress = new ArrayList<>();
//
//    @JsonManagedReference
//    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Task> completed = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public Board(String name, int boardCreatorId, List<Task> tasks, List<User> boardUsers) {
        this.name = name;
        this.boardCreatorId = boardCreatorId;
        this.tasks = tasks;
//        this.toDo = toDo;
//        this.inProgress = inProgress;
//        this.completed = completed;
        this.users = boardUsers;
    }
}
