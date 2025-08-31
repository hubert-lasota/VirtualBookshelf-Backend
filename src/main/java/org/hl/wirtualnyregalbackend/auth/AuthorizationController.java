package org.hl.wirtualnyregalbackend.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.dto.UserSignInRequest;
import org.hl.wirtualnyregalbackend.auth.dto.UserSignInResponse;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.user.dto.UserRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorizationController {

    private final AuthorizationService authorizationService;


    @PostMapping(value = "/register")
    public UserSignInResponse registerUser(@Validated(CreateGroup.class)
                                           @RequestPart("user")
                                           UserRequest userRequest,
                                           @RequestPart("profilePicture")
                                           MultipartFile profilePicture) {
        return authorizationService.registerUser(userRequest, profilePicture);
    }

    @PostMapping("/sign-in")
    public UserSignInResponse signIn(@RequestBody UserSignInRequest credentials) {
        return authorizationService.signIn(credentials);
    }

}
