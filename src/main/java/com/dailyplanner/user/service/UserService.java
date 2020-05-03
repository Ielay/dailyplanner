package com.dailyplanner.user.service;

import com.dailyplanner.user.dto.UserDTO;
import org.jetbrains.annotations.Nullable;

/**
 * @author petrov
 */
public interface UserService {

    void addUser(UserDTO userDTO);

    @Nullable UserDTO getUserById(long userId);
}
