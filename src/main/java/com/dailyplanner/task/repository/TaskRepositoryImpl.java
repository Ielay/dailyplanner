package com.dailyplanner.task.repository;

import com.dailyplanner.task.entity.TaskEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * @author lelay
 * @since 26.04.20
 */
@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private final EntityManager entityManager;

    @Autowired
    public TaskRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addTask(TaskEntity newTaskEntity) {
        try {
            entityManager.persist(newTaskEntity);
        } catch (Exception e) {
            throw new IllegalArgumentException("The entity already exist in DB", e);
        }
    }
}
