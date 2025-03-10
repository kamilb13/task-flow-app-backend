package com.taskflow.taskflowapp;

import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.model.Task;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.BoardRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import com.taskflow.taskflowapp.services.BoardService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BoardServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BoardRepository boardRepository;

    private BoardService boardService;

    @BeforeEach
    void setUp() {
        boardService = new BoardService(boardRepository, userRepository);
    }

    @Test
    void createBoard_WithValidData() {
        // given
        User user = new User(1L, "Test name", "email@email.com", "password");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Board board = new Board(1L, "Board name", 1, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<User>(), new ArrayList<Task>());

        // when
        Board newboard = boardService.createBoard(board);

        // then
        assertNotNull(newboard);
    }

    @Test
    void getBoards_WithUserId_FilteredBoards() {
        // given
        Long userId = 1L;
        User user1 = new User(1L, "User 1", "email1@email.com", "password");
        User user2 = new User(2L, "User 2", "email2@email.com", "password");

        Board board1 = new Board(1L, "Board 1", 1, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>(), new ArrayList<>());
        Board board2 = new Board(2L, "Board 2", 2, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>(), new ArrayList<>());

        board1.getUsers().add(user1);
        board2.getUsers().add(user2);

        List<Board> boards = Arrays.asList(board1, board2);

        when(boardRepository.findAll()).thenReturn(boards);

        // when
        List<Board> listBoards = boardService.getBoards(userId);

        // then
        assertNotNull(listBoards);
        assertEquals(1, listBoards.size());
        assertTrue(listBoards.get(0).getUsers().stream().anyMatch(user -> user.getId().equals(userId)));
    }

    @Test
    void deleteBoard() {
        // given
        Long boardId = 1L;
        Board board = new Board(boardId, "Board 1", 1, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>(), new ArrayList<>());

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
        doNothing().when(boardRepository).deleteById(boardId);

        // when
        boardService.deleteBoard(boardId);

        // then
        verify(boardRepository, times(1)).deleteById(boardId);

        when(boardRepository.findById(boardId)).thenReturn(Optional.empty());
        assertTrue(boardRepository.findById(boardId).isEmpty());
    }



    @Test
    void editBoard() {
    }

    @Test
    void addUserToBoard() {
        // given
        Long boardId = 1L;
        Long userId = 1L;
        User user = new User(userId, "User 1", "email1@email.com", "password");
        Board board = new Board(boardId, "Board 1", 1, LocalDateTime.now(), LocalDateTime.now(), new ArrayList<>(), new ArrayList<>());

        when(boardRepository.findById(boardId)).thenReturn(Optional.of(board));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        boardService.addUserToBoard(userId, boardId);

        // then
        assertTrue(board.getUsers().contains(user));
    }
}