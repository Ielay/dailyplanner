package com.dailyplanner.task.controller;

import com.dailyplanner.task.dto.TaskDTO;
import com.dailyplanner.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lelay
 * @since 27.04.20
 */
@RestController
@RequestMapping(path = "/api/task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity addTask(@RequestBody TaskDTO newTask) {
        try {
            taskService.addTask(newTask);

            return new ResponseEntity(HttpStatus.CREATED);
        } catch (IllegalArgumentException exc) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
}
