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

    @JsonManagedReference
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> toDo = new ArrayList<>();

    public Board(String name, int boardCreatorId, List<Task> toDo) {
        this.name = name;
        this.boardCreatorId = boardCreatorId;
        this.toDo = toDo;
    }
}
