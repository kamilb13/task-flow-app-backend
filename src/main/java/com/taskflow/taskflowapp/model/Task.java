package com.taskflow.taskflowapp.model;

import com.taskflow.taskflowapp.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "tasks")
@Entity
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Long userId;

    //nazwa kolumny "board_id"
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public Task(String title, Long userId, Board board) {
        this.title = title;
        this.userId = userId;
        this.board = board;
    }
}
