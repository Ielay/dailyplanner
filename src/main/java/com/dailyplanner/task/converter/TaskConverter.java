package com.dailyplanner.task.converter;

import com.dailyplanner.task.dto.TaskDTO;
import com.dailyplanner.task.entity.TaskEntity;
import org.springframework.stereotype.Component;

/**
 * @author lelay
 * @since 27.04.20
 */
@Component
public class TaskConverter {

    public TaskEntity toEntity(TaskDTO dto) {
        TaskEntity entity = new TaskEntity();
        entity.setTitle(dto.title);
        entity.setDescription(dto.description);
        entity.setDeadline(dto.deadline);
        entity.setDone(dto.done);
        entity.setCompletionTime(dto.completionTime);

        return entity;
    }

    public TaskDTO toDto(TaskEntity entity) {
        TaskDTO dto = new TaskDTO();
        dto.title = entity.getTitle();
        dto.description = entity.getDescription();
        dto.deadline = entity.getDeadline();
        dto.done = entity.getDone();
        dto.completionTime = entity.getCompletionTime();

        return dto;
    }
}
