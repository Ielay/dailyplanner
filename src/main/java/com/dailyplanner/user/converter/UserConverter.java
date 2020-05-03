package com.dailyplanner.user.converter;

import com.dailyplanner.user.dto.UserDTO;
import com.dailyplanner.user.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * @author petrov
 */
@Component
public class UserConverter {

    public UserDTO toDto(UserEntity entity) {
        UserDTO dto = new UserDTO();
        dto.nickname = entity.getNickname();
        dto.email = entity.getEmail();
        dto.password = entity.getPassword();

        return dto;
    }

    public UserEntity toEntity(UserDTO dto) {
        UserEntity entity = new UserEntity();
        entity.setNickname(dto.nickname);
        entity.setEmail(dto.email);
        entity.setPassword(dto.password);

        return entity;
    }
}
