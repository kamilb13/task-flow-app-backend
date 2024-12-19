package com.taskflow.taskflowapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.taskflow.taskflowapp.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "tasks")
@Entity
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Long userId;

    //nazwa kolumny "board_id"

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    @JsonBackReference
    private Board board;

    public Task(String title, String description, Long userId, Board board) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.board = board;
    }
}
