//package com.taskflow.taskflowapp.controller;
//
//import com.taskflow.taskflowapp.TaskStatus;
//import com.taskflow.taskflowapp.model.Board;
//import com.taskflow.taskflowapp.model.Task;
//import com.taskflow.taskflowapp.repository.BoardRepository;
//import com.taskflow.taskflowapp.repository.TaskRepository;
//import com.taskflow.taskflowapp.services.TaskService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//public class TaskController {
//
//    TaskService taskService;
//    BoardRepository boardRepository;
//
//    @Autowired
//    public TaskController(TaskService taskService, BoardRepository boardRepository) {
//        this.taskService = taskService;
//        this.boardRepository = boardRepository;
//    }
//}