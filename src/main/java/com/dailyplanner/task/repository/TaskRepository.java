package com.dailyplanner.task.repository;

import com.dailyplanner.task.entity.TaskEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

/**
 * @author lelay
 * @since 26.04.20
 */
public interface TaskRepository {

    void addTask(TaskEntity newTaskEntity);

    @Nullable TaskEntity findTaskById(long taskId);

    List<TaskEntity> findAllByUserId(long userId);

    int removeTask(Long taskId, long userId);

    int updateTask(long taskId, long userId, Map<String, Object> fieldToValueMap);
}
