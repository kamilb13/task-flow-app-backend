package com.taskflow.taskflowapp.controller;

import com.taskflow.taskflowapp.TaskStatus;
import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.model.Task;
import com.taskflow.taskflowapp.repository.BoardRepository;
import com.taskflow.taskflowapp.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TaskController {

    TaskService taskService;
    BoardRepository boardRepository;

    @Autowired
    public TaskController(TaskService taskService, BoardRepository boardRepository) {
        this.taskService = taskService;
        this.boardRepository = boardRepository;
    }

    @PostMapping("/create-task")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task newTask = taskService.createTask(task.getTitle(), task.getDescription(), task.getUserId(), task.getBoard().getId());
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    @GetMapping("/get-tasks")
    public ResponseEntity<?> getTasks(@RequestParam Long boardId) {
        try {
            List<Task> tasks = taskService.getAllTasksFromBoard(boardId);
            return ResponseEntity.ok(tasks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/get-task-by-status")
    public ResponseEntity<?> getTaskByStatus(@RequestParam Long boardId, @RequestParam TaskStatus status) {
        try {
            List<Task> tasks = taskService.getTasksByStatus(boardId, status);
            return ResponseEntity.ok(tasks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/change-status")
    public ResponseEntity<?> changeTaskStatus(@RequestParam Long taskId, @RequestParam TaskStatus newStatus) {
        try {
            Task task = taskService.changeStatusOfTask(taskId, newStatus);
            return ResponseEntity.ok(task);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}