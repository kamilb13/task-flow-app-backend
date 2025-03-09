package com.taskflow.taskflowapp;

import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.model.Task;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.BoardRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import com.taskflow.taskflowapp.services.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    void createBoard_WithValidData() {
        // given
        User user = new User("Test name", "email@email.com", "password");
        userRepository.save(user);
        Board board = new Board(1L, "Board name", 1, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<User>(), new ArrayList<Task>());
        Optional<User> creator = userRepository.findById((long) board.getBoardCreatorId());
        BoardService boardService = new BoardService(boardRepository, userRepository);

        // when
        Board newboard = boardService.createBoard(board);

        // then
        assertNotNull(newboard);
    }

    @Test
    void getBoards() {
    }

    @Test
    void deleteBoard() {
    }

    @Test
    void editBoard() {
    }

    @Test
    void addUserToBoard() {
    }
}