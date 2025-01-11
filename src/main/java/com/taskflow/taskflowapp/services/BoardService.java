package com.taskflow.taskflowapp.services;

import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.BoardRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    public Board createBoard(Board board) {
        Optional<User> creator = userRepository.findById((long) board.getBoardCreatorId());
        if (creator.isEmpty()) {
            throw new IllegalArgumentException("Creator not found");
        }
        Board newBoard = new Board(board.getName(), board.getBoardCreatorId(), new ArrayList<>(), new ArrayList<>(), board.getEstimatedEndDate());
        newBoard.getUsers().add(creator.get());
        boardRepository.save(newBoard);
        return newBoard;
    }

    public List<Board> getBoards(Long userId) {
        List<Board> boards = boardRepository.findAll();
        if (userId != null) {
            boards = boards.stream()
                    .filter(board -> board.getUsers().stream().anyMatch(user -> user.getId() == userId))
                    .collect(Collectors.toList());
        }
        return boards;
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    public Board editBoard(Long boardId, Board updatedBoard) {
        Optional<Board> boardToUpdate = boardRepository.findById(boardId);
        Board editBoard = boardToUpdate.get();
        editBoard.setName(updatedBoard.getName());
        boardRepository.save(editBoard);
        return editBoard;
    }
}