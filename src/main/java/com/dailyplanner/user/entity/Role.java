package com.dailyplanner.user.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author petrov
 */
public enum Role implements GrantedAuthority {
    ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }

    public static Role findByAuthority(String authority) {
        Role[] roles = Role.values();
        for (Role role : roles) {
            if (role.getAuthority().equals(authority)) {
                return role;
            }
        }

        throw new IllegalArgumentException(String.format("Incorrect value of authority was passed: '%s'", authority));
    }
}
