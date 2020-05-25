package com.dailyplanner.task.service;

import com.dailyplanner.task.dto.TaskDTO;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author lelay
 * @since 27.04.20
 */
public interface TaskService {

    void addTask(TaskDTO newTask);

    @Nullable TaskDTO getTaskById(long taskId);

    @Nullable List<TaskDTO> getAllTasks(long userId);
}
