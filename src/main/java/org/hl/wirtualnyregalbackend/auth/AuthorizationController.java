package org.hl.wirtualnyregalbackend.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.dto.UserCredentialsRequest;
import org.hl.wirtualnyregalbackend.auth.dto.UserSignInResponse;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.user.dto.UserRequest;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorizationController {

    private final AuthorizationService authorizationService;


    @PostMapping("/register")
    public UserSignInResponse registerUser(@Validated(CreateGroup.class)
                                           @RequestPart("user")
                                           UserRequest userRequest,
                                           @RequestPart(value = "profilePicture", required = false)
                                           MultipartFile profilePicture) {
        return authorizationService.registerUser(userRequest, profilePicture);
    }

    @PostMapping("/sign-in")
    public UserSignInResponse signIn(@Validated(CreateGroup.class) @RequestBody UserCredentialsRequest credentials) {
        return authorizationService.signIn(credentials);
    }

    @PatchMapping("/credentials")
    public UserResponse updateCredentials(@Validated(UpdateGroup.class)
                                          @RequestBody
                                          UserCredentialsRequest credentials,
                                          @AuthenticationPrincipal User user) {
        return authorizationService.updateUserCredentials(user, credentials);
    }

}
