package org.hl.wirtualnyregalbackend.auth;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.dto.UserCredentialsDto;
import org.hl.wirtualnyregalbackend.auth.dto.UserSignInResponse;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    // TODO raczej do usuniecia
    @GetMapping("/verify-jwt-validity")
    public ResponseEntity<?> verifyJwt(@RequestParam String jwt) {
        boolean isValid = authorizationService.isJwtValid(jwt);
        Map<String, Object> response = Map.of("isValid", isValid);
        return ResponseEntity.ok(response);
    }

}
