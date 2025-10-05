package org.hl.wirtualnyregalbackend.user;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.user.dto.UpdateGenrePreferencesRequest;
import org.hl.wirtualnyregalbackend.user.dto.UserPageResponse;
import org.hl.wirtualnyregalbackend.user.dto.UserProfileDto;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;
import org.hl.wirtualnyregalbackend.user.model.UserFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/users")
class UserController {

    private final UserQueryService query;
    private final UserCommandService command;


    @PostMapping("/upload-profile-picture")
    public UserResponse uploadProfilePicture(@RequestPart("profilePicture") MultipartFile profilePicture,
                                             @AuthenticationPrincipal User user) {
        return command.uploadProfilePicture(user, profilePicture);
    }

    @PatchMapping("/profile")
    public UserResponse updateUserProfile(@Validated(UpdateGroup.class)
                                          @RequestBody
                                          UserProfileDto profileDto,
                                          @AuthenticationPrincipal
                                          User user) {
        return command.updateUserProfile(profileDto, user);
    }

    @PutMapping("/genre-preferences")
    public UserResponse updateGenrePreferences(@RequestBody UpdateGenrePreferencesRequest request,
                                               @AuthenticationPrincipal User user) {
        return command.updateGenrePreferences(user, request);
    }

    @GetMapping
    public UserPageResponse findUsers(UserFilter filter, Pageable pageable) {
        return query.findUsers(filter, pageable);
    }

}
