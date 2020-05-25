package com.dailyplanner.user.service;

import com.dailyplanner.user.converter.UserConverter;
import com.dailyplanner.user.dto.UserDTO;
import com.dailyplanner.user.entity.Role;
import com.dailyplanner.user.entity.UserEntity;
import com.dailyplanner.user.repository.UserRepository;
import com.dailyplanner.user.security.JwtTokenProvider;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author petrov
 */
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private UserConverter userConverter;

    private JwtTokenProvider jwtTokenProvider;

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverter userConverter,
                           JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String addUser(UserDTO userDTO) {
        UserEntity userEntity = userConverter.toEntity(userDTO);
        userEntity.setRole(Role.ROLE_USER);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));

        if (userRepository.findUserByNickname(userEntity.getNickname()) == null) {
            userRepository.addUser(userEntity);

            return jwtTokenProvider.createToken(userEntity.getNickname(), userEntity.getRole());
        } else {
            throw new IllegalArgumentException(String.format("User with username '%s' already exists", userEntity.getNickname()));
        }
    }

    @Override
    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username, userRepository.findUserByNickname(username).getRole());
        } catch (NullPointerException | AuthenticationException exc) {
            throw new IllegalArgumentException("User wasn't found", exc);
        }
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

    @Override
    public long getRequestSenderId(HttpServletRequest req) {
        return userRepository.findUserByNickname(
                jwtTokenProvider.getUsername(
                        jwtTokenProvider.getToken(req)
                )
        ).getId();
    }
}
