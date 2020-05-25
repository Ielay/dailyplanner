package com.dailyplanner.user.configuration;

import com.dailyplanner.user.repository.UserRepository;
import com.dailyplanner.user.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author petrov
 */
@ComponentScan(basePackages = {"com.dailyplanner.user"})
public class UserConfiguration {

    @Bean("userDetailsServiceImpl")
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return new UserDetailsServiceImpl(userRepository);
    }
}
