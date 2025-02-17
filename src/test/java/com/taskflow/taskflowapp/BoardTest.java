package com.taskflow.taskflowapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.BoardRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BoardTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        boardRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testCreateBoard() throws Exception {
        User creator = new User("John", "john@john.com");
        userRepository.save(creator);
        Board board = new Board("Test Board", Math.toIntExact(creator.getId()), new ArrayList<>(), new ArrayList<>(), LocalDateTime.now() );

        mockMvc.perform(post("/create-board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(board)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Board"))
                .andExpect(jsonPath("$.boardCreatorId").value(creator.getId()));
    }

    @Test
    public void testCreateBoardWithInvalidUser() throws Exception {
        Board board = new Board("Test Board", 9999, new ArrayList<>(), new ArrayList<>(), LocalDateTime.now());

        mockMvc.perform(post("/create-board")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(board)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Creator not found"));
    }

    @Test
    public void testGetBoardsForUser() throws Exception {
        User user = new User("Jan", "Doe");
        userRepository.save(user);

        Board board = new Board();
        board.setName("User Board");
        board.setBoardCreatorId(Math.toIntExact(user.getId()));
        board.getUsers().add(user);
        boardRepository.save(board);

        mockMvc.perform(get("/get-boards")
                        .param("userId", String.valueOf(user.getId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("User Board"))
                .andExpect(jsonPath("$[0].boardCreatorId").value(user.getId()));
    }

    @Test
    public void testGetBoardsForNonexistentUser() throws Exception {
        Board board = new Board();
        board.setName("Test board");
        boardRepository.save(board);

        mockMvc.perform(get("/get-boards")
                        .param("userId", "999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
