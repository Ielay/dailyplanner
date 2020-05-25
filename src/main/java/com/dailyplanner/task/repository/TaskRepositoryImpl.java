package com.dailyplanner.task.repository;

import com.dailyplanner.task.entity.TaskEntity;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * @author lelay
 * @since 26.04.20
 */
@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public TaskRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void addTask(TaskEntity newTaskEntity) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(newTaskEntity);
        } catch (Exception e) {
            throw new IllegalArgumentException("The entity already exist in DB", e);
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public @Nullable TaskEntity findTaskById(long taskId) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            return entityManager.find(TaskEntity.class, taskId);
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public List<TaskEntity> findAllByUserId(long userId) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            return entityManager.createQuery("SELECT t FROM TaskEntity AS t WHERE t.userId = :userId", TaskEntity.class)
                    .setParameter("userId", userId)
                    .getResultList();
        } finally {
            entityManager.getTransaction().commit();
        }
    }
}
