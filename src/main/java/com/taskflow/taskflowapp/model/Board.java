package com.taskflow.taskflowapp.model;

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
@ToString
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int boardCreatorId;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Task> toDo = new ArrayList<>();

    public Board(String name, int boardCreatorId, List<Task> toDo) {
        this.name = name;
        this.boardCreatorId = boardCreatorId;
        this.toDo = toDo;
    }
}
