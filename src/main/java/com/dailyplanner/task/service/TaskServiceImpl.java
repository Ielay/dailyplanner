package com.dailyplanner.task.service;

import com.dailyplanner.task.converter.TaskConverter;
import com.dailyplanner.task.dto.TaskDTO;
import com.dailyplanner.task.entity.TaskEntity;
import com.dailyplanner.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lelay
 * @since 27.04.20
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskConverter taskConverter;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskConverter taskConverter) {
        this.taskRepository = taskRepository;
        this.taskConverter = taskConverter;
    }

    @Override
    public void addTask(TaskDTO newTask) {
        TaskEntity newTaskEntity = taskConverter.toEntity(newTask);

        taskRepository.addTask(newTaskEntity);
    }
}
