package com.taskflow.taskflowapp.controller;

import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.repository.BoardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class BoardController {

    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @PostMapping("/create-board")
    public ResponseEntity<?> createBoard(@RequestBody Board board) {
        Board newBoard = new Board(board.getName(), board.getBoardCreatorId(), new ArrayList<>());
        boardRepository.save(newBoard);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBoard);
    }
}
