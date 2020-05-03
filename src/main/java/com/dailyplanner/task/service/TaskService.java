package com.dailyplanner.task.service;

import com.dailyplanner.task.dto.TaskDTO;
import org.jetbrains.annotations.Nullable;

/**
 * @author lelay
 * @since 27.04.20
 */
public interface TaskService {

    void addTask(TaskDTO newTask);

    @Nullable TaskDTO getTaskById(long taskId);
}
