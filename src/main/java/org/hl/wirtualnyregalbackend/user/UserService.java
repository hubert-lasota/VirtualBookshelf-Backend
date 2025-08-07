package org.hl.wirtualnyregalbackend.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserService {

    private final static Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public User findUserEntityById(Long id) throws EntityNotFoundException {
        Optional<User> userOpt = id != null ? userRepository.findById(id) : Optional.empty();
        return userOpt.orElseThrow(() -> new EntityNotFoundException("User with id = '%d' not found.".formatted(id)));
    }

}
