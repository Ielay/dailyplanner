package com.dailyplanner.user.service;

import com.dailyplanner.user.dto.UserDTO;
import org.jetbrains.annotations.Nullable;

import javax.servlet.http.HttpServletRequest;

/**
 * @author petrov
 */
public interface UserService {

    String addUser(UserDTO userDTO);

    String login(String username, String password);

    @Nullable UserDTO getUserById(long userId);

    long getRequestSenderId(HttpServletRequest req);
}
