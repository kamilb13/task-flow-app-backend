package com.taskflow.taskflowapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskflow.taskflowapp.dto.LoginRequest;
import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.model.Role;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Before
    public void setUp() {
        taskRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testAddTask() throws Exception {
        User user = new User("Jan", "Doe");
        userRepository.save(user);
        Board board = new Board("Test Board", Math.toIntExact(user.getId()), new ArrayList<>(), new ArrayList<>(), LocalDateTime.now());
        boardRepository.save(board);
        Task task = new Task("Task 1", "Description 1", user.getId(), board, TaskStatus.TO_DO, 1);

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

    @Test
    public void testDeleteTask() throws Exception {
        Optional<User> existingUser = userRepository.findByUsername("admin");
        if (existingUser.isEmpty()) {
            userRepository.save(new User("admin", "admin@admin.com", passwordEncoder.encode("admin")));
        }

        LoginRequest loginRequest = new LoginRequest("admin", "admin");
        String response = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn().getResponse().getContentAsString();

        String token = objectMapper.readTree(response).path("token").asText();

        User user = userRepository.findByUsername("admin").get();
        Board board = new Board("Test Board", Math.toIntExact(user.getId()), new ArrayList<>(), new ArrayList<>(), LocalDateTime.now());
        boardRepository.save(board);
        Task task = new Task("Task 1", "Description", user.getId(), board, TaskStatus.TO_DO, 1);
        taskRepository.save(task);

        mockMvc.perform(delete("/delete-task")
                        .header("Authorization", "Bearer " + token)
                        .param("taskId", String.valueOf(task.getId())))
                .andExpect(status().isOk());

        assertFalse(taskRepository.findById(task.getId()).isPresent());
    }
}
