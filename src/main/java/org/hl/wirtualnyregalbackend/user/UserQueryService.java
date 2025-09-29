package org.hl.wirtualnyregalbackend.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.user.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class UserQueryService implements UserDetailsService {

    private final UserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository
            .findByUsername(username)
            .orElseThrow(() -> {
                String message = "User not found with username: %s".formatted(username);
                log.warn(message);
                return new UsernameNotFoundException(message);
            });
    }

    public User findUserById(Long userId) throws UserNotFoundException {
        Optional<User> userOpt = userId != null ? repository.findById(userId) : Optional.empty();
        return userOpt.orElseThrow(() -> {
            log.warn("User not found with ID: {}", userId);
            return new UserNotFoundException(userId);
        });
    }

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

}
