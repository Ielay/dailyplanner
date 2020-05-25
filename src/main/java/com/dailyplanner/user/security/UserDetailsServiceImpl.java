package com.dailyplanner.user.security;

import com.dailyplanner.user.entity.UserEntity;
import com.dailyplanner.user.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author petrov
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        UserEntity foundUser = userRepository.findUserByNickname(nickname);

        if (foundUser == null) {
            throw new UsernameNotFoundException(String.format("User with username '%s' wasn't found", nickname));
        }

        return User.builder()
                .username(nickname)
                .password(foundUser.getPassword())
                .authorities(foundUser.getRole())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
