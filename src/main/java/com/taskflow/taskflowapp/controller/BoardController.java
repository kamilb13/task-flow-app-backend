package com.taskflow.taskflowapp.controller;

import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.BoardRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BoardController {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Autowired
    public BoardController(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/create-board")
    public ResponseEntity<?> createBoard(@RequestBody Board board) {
        Optional<User> creator = userRepository.findById((long) board.getBoardCreatorId());
        if (creator.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Creator not found");
        }
        Board newBoard = new Board(board.getName(), board.getBoardCreatorId(), new ArrayList<>(), new ArrayList<>());
        newBoard.getUsers().add(creator.get());
        boardRepository.save(newBoard);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBoard);
    }

    @GetMapping("/get-boards")
    public ResponseEntity<?> getBoards(@RequestParam Long userId) {
        List<Board> boards = boardRepository.findAll();
        if (userId != null) {
            boards = boards.stream()
                    .filter(board -> board.getUsers().stream().anyMatch(user -> user.getId() == userId))
                    .collect(Collectors.toList());
        }
        return ResponseEntity.ok(boards);
    }
}
