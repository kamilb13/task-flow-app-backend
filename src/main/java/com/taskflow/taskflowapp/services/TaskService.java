package com.taskflow.taskflowapp.services;

import com.taskflow.taskflowapp.TaskStatus;
import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.model.Task;
import com.taskflow.taskflowapp.repository.BoardRepository;
import com.taskflow.taskflowapp.repository.TaskRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Task  createTask(String title, String description, Long userId, Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board not found with id: " + boardId));

        Task task = new Task(title, description,userId, board);
        return taskRepository.save(task);
    }
}