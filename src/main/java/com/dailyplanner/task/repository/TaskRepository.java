package com.dailyplanner.task.repository;

import com.dailyplanner.task.entity.TaskEntity;
import org.jetbrains.annotations.Nullable;

/**
 * @author lelay
 * @since 26.04.20
 */
public interface TaskRepository {

    void addTask(TaskEntity newTaskEntity);

    @Nullable TaskEntity findTaskById(long taskId);
}
