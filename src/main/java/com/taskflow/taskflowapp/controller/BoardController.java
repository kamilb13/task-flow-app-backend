package com.taskflow.taskflowapp.controller;

import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/create-board")
    public ResponseEntity<?> createBoard(@RequestBody Board board) {
        try {
            Board newBoard = boardService.createBoard(board);
            return ResponseEntity.status(HttpStatus.CREATED).body(newBoard);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("delete-board")
    public ResponseEntity<?> deleteBoard(@RequestBody Board board) {
        try {boardService.deleteBoard(board.getId());
            return ResponseEntity.ok().body("Board successfully deleted");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get-boards")
    public ResponseEntity<?> getBoards(@RequestParam Long userId) {
        try {
            List<Board> boards = boardService.getBoards(userId);
            return ResponseEntity.status(HttpStatus.CREATED).body(boards);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
