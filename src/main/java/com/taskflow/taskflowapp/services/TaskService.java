package com.taskflow.taskflowapp.services;

import com.taskflow.taskflowapp.TaskStatus;
import com.taskflow.taskflowapp.model.Board;
import com.taskflow.taskflowapp.model.Task;
import com.taskflow.taskflowapp.model.User;
import com.taskflow.taskflowapp.repository.BoardRepository;
import com.taskflow.taskflowapp.repository.TaskRepository;
import com.taskflow.taskflowapp.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        int maxPosition = taskRepository.findByBoardIdAndStatus(boardId, TaskStatus.TO_DO)
                .stream()
                .mapToInt(Task::getPosition)
                .max()
                .orElse(0);
        Task task = new Task(title, description, user.getId(), board, TaskStatus.TO_DO, maxPosition + 1);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasksFromBoard(Long boardId) {
        List<Task> tasks = taskRepository.findByBoardId(boardId);

        if (tasks.isEmpty()) {
            return Collections.emptyList();
        }
        return tasks;
    }

    public List<Task> getTasksByStatus(Long boardId, TaskStatus status) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("Board not found with this id: " + boardId));
        return board.getTasks().stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    public Task changeStatusOfTask(Long taskId, TaskStatus newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + taskId));
        task.setStatus(newStatus);
        updateTaskPositionsAfterChange();
        return taskRepository.save(task);
    }

    public Task editTask(Task updatedTask) {
        Optional<Task> optionalTask = taskRepository.findById(updatedTask.getId());
        if (optionalTask.isPresent()) {
            Task taskToEdit = optionalTask.get();
            taskToEdit.setTitle(updatedTask.getTitle());
            taskToEdit.setDescription(updatedTask.getDescription());
            return taskRepository.save(taskToEdit);
        } else {
            throw new EntityNotFoundException("Task not found with this id");
        }
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
        updateTaskPositionsAfterChange();
    }

    public void updateTaskPositionsAfterChange() {
        List<Task> tasks = taskRepository.findAll();
        List<Task> tasksToDo = tasks.stream().filter(task -> task.getStatus().name().equals("TO_DO")).collect(Collectors.toList());
        List<Task> tasksInProgress = tasks.stream().filter(task -> task.getStatus().name().equals("IN_PROGRESS")).collect(Collectors.toList());
        List<Task> tasksCompleted = tasks.stream().filter(task -> task.getStatus().name().equals("COMPLETED")).collect(Collectors.toList());
        updatePositionsInCategory(tasksToDo);
        updatePositionsInCategory(tasksInProgress);
        updatePositionsInCategory(tasksCompleted);
    }

    private void updatePositionsInCategory(List<Task> tasksInCategory) {
        tasksInCategory.sort(Comparator.comparingInt(Task::getPosition));
        for (int i = 0; i < tasksInCategory.size(); i++) {
            Task task = tasksInCategory.get(i);
            task.setPosition(i + 1);
            taskRepository.save(task);
        }
    }

    public Task editTaskPosition(Task updatedTask) {
        Task taskToEdit = taskRepository.findById(updatedTask.getId())
                .orElseThrow(() -> new EntityNotFoundException("Task not found with this id"));
        List<Task> tasks = taskRepository.findByBoardIdAndStatus(
                        taskToEdit.getBoard().getId(),
                        taskToEdit.getStatus()
                )
                .stream()
                .sorted(Comparator.comparingInt(Task::getPosition))
                .collect(Collectors.toList());
        tasks.remove(taskToEdit);
        tasks.add(updatedTask.getPosition() - 1, taskToEdit);
        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).setPosition(i + 1);
        }
        taskRepository.saveAll(tasks);
        return taskToEdit;
    }

}