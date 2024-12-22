package com.taskflow.taskflowapp;

import com.taskflow.taskflowapp.repository.TaskRepository;
import com.taskflow.taskflowapp.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskTest {

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    public void testAddTask() {

    }
}
