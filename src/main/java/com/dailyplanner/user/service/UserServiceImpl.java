package com.dailyplanner.user.service;

import com.dailyplanner.user.converter.UserConverter;
import com.dailyplanner.user.dto.UserDTO;
import com.dailyplanner.user.entity.UserEntity;
import com.dailyplanner.user.repository.UserRepository;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author petrov
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserConverter userConverter;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    @Override
    public void addUser(UserDTO userDTO) {
        UserEntity userEntity = userConverter.toEntity(userDTO);

        userRepository.addUser(userEntity);
    }

    @Override
    public @Nullable UserDTO getUserById(long userId) {
        UserEntity foundEntity = userRepository.findUserById(userId);

        if (foundEntity == null) {
            //TODO: log user wasn't found

            return null;
        }

        UserDTO userDTO = userConverter.toDto(foundEntity);

        //TODO: log found user

        return userDTO;
    }
}
