package com.dailyplanner.task.service;

import com.dailyplanner.task.converter.TaskConverter;
import com.dailyplanner.task.dto.TaskDTO;
import com.dailyplanner.task.entity.TaskEntity;
import com.dailyplanner.task.repository.TaskRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public @Nullable List<TaskDTO> getAllTasks(long userId) {
        List<TaskEntity> taskEntities = taskRepository.findAllByUserId(userId);

        List<TaskDTO> dtos = new ArrayList<>(taskEntities.size());
        for (TaskEntity entity : taskEntities) {
            dtos.add(taskConverter.toDto(entity));
        }

        return dtos;
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

    @Override
    public void removeTask(Long taskId, long userId) {
        int removedRows = taskRepository.removeTask(taskId, userId);

        if (removedRows != 1) {
            throw new IllegalArgumentException("Incorrect taskId " + taskId + "- the task was already removed");
        }
    }

    @Override
    public void updateTask(Long taskId, long userId, TaskDTO taskDTO) {
        Map<String, Object> fieldToValueMap = getAllFieldsWithValues(taskDTO);

        System.out.println(taskId);
        System.out.println(userId);
        System.out.println(taskDTO);
        System.out.println(Arrays.toString(fieldToValueMap.entrySet().toArray()));

        if (fieldToValueMap.isEmpty()) {
            return;
        }

        int updatedRows = taskRepository.updateTask(taskId, userId, fieldToValueMap);

        if (updatedRows != 1) {
            throw new IllegalArgumentException("Incorrect taskId " + taskId + "- such task doesn't exists");
        }
    }

    private Map<String, Object> getAllFieldsWithValues(TaskDTO taskDTO) {
        var map = new HashMap<String, Object>();

        if (taskDTO.done != null) {
            map.put("done", taskDTO.done);
        }

        //TODO: support another possible fields updates

        return map;
    }
}
