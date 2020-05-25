package com.dailyplanner.user.converter.attribute;

import com.dailyplanner.user.entity.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author petrov
 */
@Converter
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        return role.getAuthority();
    }

    @Override
    public Role convertToEntityAttribute(String authority) {
        return Role.findByAuthority(authority);
    }
}
