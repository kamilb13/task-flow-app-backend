package com.taskflow.taskflowapp.services;

import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.model.Task;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.BoardRepository;
import com.taskflow.taskflowapp.repository.TaskRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    TaskRepository taskRepository;
    UserRepository userRepository;
    BoardRepository boardRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository, BoardRepository boardRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    public Task createTask(String title, String description, Long userId, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found with this id " + boardId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with this id " + userId));
        Task task = new Task(title, description, user.getId(), board);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}