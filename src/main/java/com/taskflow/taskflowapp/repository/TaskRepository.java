package com.taskflow.taskflowapp.repository;

import com.taskflow.taskflowapp.TaskStatus;
import com.taskflow.taskflowapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByBoardId(Long boardId);
    List<Task> findByBoardIdAndStatus(Long boardId, TaskStatus status);
}
