package com.dailyplanner.user.repository;

import com.dailyplanner.user.entity.UserEntity;
import org.jetbrains.annotations.Nullable;

/**
 * @author petrov
 */
public interface UserRepository {

    void addUser(UserEntity userEntity);

    @Nullable UserEntity findUserById(long userId);
}
