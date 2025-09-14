package org.hl.wirtualnyregalbackend.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.user.dto.UserProfileDto;
import org.hl.wirtualnyregalbackend.user.entity.UserProfile;
import org.hl.wirtualnyregalbackend.user.exception.UserNotFoundException;
import org.hl.wirtualnyregalbackend.user_profile_picture.UserProfilePictureService;
import org.hl.wirtualnyregalbackend.user_profile_picture.entity.UserProfilePicture;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserProfilePictureService userProfilePictureService;

    @Transactional
    public UserProfile createUserProfile(UserProfileDto profileDto, MultipartFile profilePicture, User user) {
        UserProfilePicture userProfilePicture = userProfilePictureService.createUserProfilePicture(profileDto.pictureUrl(), profilePicture);
        return UserMapper.toUserProfile(profileDto, user, userProfilePicture);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
            .findByUsername(username)
            .orElseThrow(() -> {
                String message = "User not found with username: %s".formatted(username);
                log.warn(message);
                return new UsernameNotFoundException(message);
            });
    }

    public User findUserById(Long userId) throws UserNotFoundException {
        Optional<User> userOpt = userId != null ? userRepository.findById(userId) : Optional.empty();
        return userOpt.orElseThrow(() -> {
            log.warn("User not found with ID: {}", userId);
            return new UserNotFoundException(userId);
        });
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

}
