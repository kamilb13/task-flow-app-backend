package com.taskflow.taskflowapp.controller;

import com.taskflow.taskflowapp.model.Task;

import java.util.List;

public interface Board {
    void addList();
    void removeList();
    List<Task> getList();
}
