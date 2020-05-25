package com.dailyplanner.user.repository;

import com.dailyplanner.user.entity.UserEntity;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;

/**
 * @author petrov
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public UserRepositoryImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void addUser(UserEntity newUserEntity) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entityManager.persist(newUserEntity);
        } catch (Exception exc) {
            throw new IllegalArgumentException("The entity already exist in DB", exc);
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public @Nullable UserEntity findUserById(long userId) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            return entityManager.find(UserEntity.class, userId);
        } finally {
            entityManager.getTransaction().commit();
        }
    }

    @Override
    public @Nullable UserEntity findUserByNickname(String nickname) {
        var entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            return entityManager.createQuery("SELECT u FROM UserEntity as u WHERE u.nickname = :nick", UserEntity.class)
                    .setParameter("nick", nickname)
                    .getSingleResult();
        } catch (NoResultException exc) {
            return null;
        } finally {
            entityManager.getTransaction().commit();
        }
    }
}
