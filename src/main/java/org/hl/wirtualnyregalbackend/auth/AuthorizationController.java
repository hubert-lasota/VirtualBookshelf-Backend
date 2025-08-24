package org.hl.wirtualnyregalbackend.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.dto.UserCredentialsDto;
import org.hl.wirtualnyregalbackend.auth.dto.UserSignInResponse;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorizationController {

    private final AuthorizationService authorizationService;


    @PostMapping("/register")
    public UserSignInResponse registerUser(@Validated(CreateGroup.class) @RequestBody UserCredentialsDto credentials) {
        return authorizationService.registerUser(credentials);
    }

    @PostMapping("/sign-in")
    public UserSignInResponse signIn(@RequestBody UserCredentialsDto credentials) {
        return authorizationService.signIn(credentials);
    }

}
