package com.dailyplanner.task.repository;

import com.dailyplanner.task.entity.TaskEntity;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public int removeTask(Long taskId, long userId) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            return entityManager.createQuery("DELETE FROM TaskEntity AS t WHERE t.id = :taskId AND t.userId = :userId")
                    .setParameter("taskId", taskId)
                    .setParameter("userId", userId)
                    .executeUpdate();
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public int updateTask(long taskId, long userId, Map<String, Object> fieldToValueMap) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            String query = "UPDATE TaskEntity AS t SET %s WHERE t.id = %s AND t.userId = %s;";
            List<String> conditions = fieldToValueMap.entrySet().stream()
                    .map(entry ->
                            (entry.getKey() + " = " +
                            (entry.getValue().getClass() == String.class ?
                                    "'" + entry.getValue() + "'" :
                                    entry.getValue())
                            )
                    )
                    .collect(Collectors.toList());

            String finalQuery = String.format(query,
                    String.join(" AND ", conditions),
                    taskId,
                    userId
            );

            System.out.println(finalQuery);

            return entityManager.createQuery(
                finalQuery
            ).executeUpdate();
        } finally {
            entityManager.getTransaction().commit();
        }
    }
}
