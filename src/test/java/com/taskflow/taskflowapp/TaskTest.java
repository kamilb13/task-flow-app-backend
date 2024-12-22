package com.taskflow.taskflowapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.model.Task;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.BoardRepository;
import com.taskflow.taskflowapp.repository.TaskRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import com.taskflow.taskflowapp.services.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TaskTest {

    @Autowired
    TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    BoardRepository boardRepository;

    @Before
    public void setUp() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testAddTask() throws Exception {
        User user = new User("Jan", "Doe");
        userRepository.save(user);
        Board board = new Board("Test Board", Math.toIntExact(user.getId()), new ArrayList<>(), new ArrayList<>());
        boardRepository.save(board);
        Task task = new Task("Task 1", "Description 1", user.getId(), board);

        String taskJson = """
                {
                    "title": "zadanie 3",
                    "description": "opis zadania do wykonania",
                    "userId": %d,
                    "board": {
                        "id": %d
                    }
                }
                """.formatted(user.getId(), board.getId());

        mockMvc.perform(post("/create-task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(taskJson))
                .andExpect(status().isCreated());
    }
}
