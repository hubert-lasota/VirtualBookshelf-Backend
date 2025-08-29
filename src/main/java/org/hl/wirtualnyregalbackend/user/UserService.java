package org.hl.wirtualnyregalbackend.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User findUserById(Long userId) throws UserNotFoundException {
        Optional<User> userOpt = userId != null ? userRepository.findById(userId) : Optional.empty();
        return userOpt.orElseThrow(() -> {
            log.warn("User not found with ID: {}", userId);
            return new UserNotFoundException(userId);
        });
    }

}
