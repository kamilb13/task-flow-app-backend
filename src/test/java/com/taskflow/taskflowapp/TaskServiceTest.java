package com.taskflow.taskflowapp;

import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.model.Task;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.BoardRepository;
import com.taskflow.taskflowapp.repository.TaskRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import com.taskflow.taskflowapp.services.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TaskServiceTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private TaskService taskService;

    @Test
    void createTask_ShouldCreateAndSaveTask() {
        // Given
        User user = new User();
        user.setUsername("Test User");
        user.setEmail("test@test.com");
        user.setPassword("password");
        user = userRepository.save(user);

        Board board = new Board();
        board.setName("Test Board");
        board = boardRepository.save(board);

        String title = "New Task";
        String description = "Task Description";

        // When
        Task createdTask = taskService.createTask(title, description, user.getId(), board.getId());

        // Then
        assertNotNull(createdTask.getId());
        assertEquals(title, createdTask.getTitle());
        assertEquals(description, createdTask.getDescription());
        assertEquals(TaskStatus.TO_DO, createdTask.getStatus());
        assertEquals(board.getId(), createdTask.getBoard().getId());

        Task foundTask = taskRepository.findById(createdTask.getId()).orElse(null);
        assertNotNull(foundTask);
    }
}
