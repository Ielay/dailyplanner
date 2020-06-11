package com.dailyplanner.task.controller;

import com.dailyplanner.task.dto.TaskDTO;
import com.dailyplanner.task.service.TaskService;
import com.dailyplanner.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lelay
 * @since 27.04.20
 */
@RestController
@RequestMapping(path = "/api/task")
public class TaskController {

    private final TaskService taskService;

    private final UserService userService;

    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostMapping(path = "/add")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity addTask(HttpServletRequest request, @RequestBody TaskDTO newTask) {
        try {
            newTask.userId = userService.getRequestSenderId(request);
            taskService.addTask(newTask);

            return new ResponseEntity(HttpStatus.CREATED);
        } catch (IllegalArgumentException exc) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @GetMapping(path = "/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<TaskDTO>> getAllTasks(HttpServletRequest request) {
        try {
            long requestSenderId = userService.getRequestSenderId(request);
            List<TaskDTO> userTasks = taskService.getAllTasks(requestSenderId);

            return new ResponseEntity<>(userTasks, HttpStatus.OK);
        } catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{taskId}/delete")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity removeTask(HttpServletRequest request, @PathVariable Long taskId) {
        try {
            long requestSenderId = userService.getRequestSenderId(request);
            taskService.removeTask(taskId, requestSenderId);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PatchMapping(path = "/{taskId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity patchTask(HttpServletRequest request, @PathVariable Long taskId, @RequestBody TaskDTO fieldsToUpdate) {
        try {
            long requestSenderId = userService.getRequestSenderId(request);
            taskService.updateTask(taskId, requestSenderId, fieldsToUpdate);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exc) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
