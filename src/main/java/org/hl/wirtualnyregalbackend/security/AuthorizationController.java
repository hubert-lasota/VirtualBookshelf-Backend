package org.hl.wirtualnyregalbackend.security;

import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.security.dto.UserCredentialsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
class AuthorizationController {

    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@Validated(CreateGroup.class) @RequestBody UserCredentialsDto credentials) {
        var response = authorizationService.registerUser(credentials);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/sign-in")
    public ResponseEntity<?> signIn(@RequestBody UserCredentialsDto credentials) {
        var response = authorizationService.signIn(credentials);
        return ResponseEntity.ok(response);
    }

    // TODO raczej do usuniecia
    @GetMapping(value = "/verify-jwt-validity")
    public ResponseEntity<?> verifyJwt(@RequestParam String jwt) {
        boolean isValid = authorizationService.isJwtValid(jwt);
        Map<String, Object> response = Map.of("isValid", isValid);
        return ResponseEntity.ok(response);
    }

}
