package com.dailyplanner.task.service;

import com.dailyplanner.task.converter.TaskConverter;
import com.dailyplanner.task.dto.TaskDTO;
import com.dailyplanner.task.entity.TaskEntity;
import com.dailyplanner.task.repository.TaskRepository;
import org.jetbrains.annotations.Nullable;
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

    @Override
    public @Nullable TaskDTO getTaskById(long taskId) {
        TaskEntity foundTask = taskRepository.findTaskById(taskId);

        if (foundTask == null) {
            //TODO: log nothing was found here

            return null;
        }

        TaskDTO taskDTO = taskConverter.toDto(foundTask);

        //TODO: log found result here

        return taskDTO;
    }
}
