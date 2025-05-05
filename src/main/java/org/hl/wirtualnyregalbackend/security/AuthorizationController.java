package org.hl.wirtualnyregalbackend.security;

import org.hl.wirtualnyregalbackend.security.model.dto.UserCredentialsDto;
import org.hl.wirtualnyregalbackend.security.model.dto.UserSignInResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
class AuthorizationController {

    private final AuthorizationService authorizationService;

    AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody UserCredentialsDto credentials) {
        UserSignInResponseDto response = authorizationService.registerUser(credentials);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/sign-in")
    public ResponseEntity<?> signIn(@RequestBody UserCredentialsDto credentials) {
        UserSignInResponseDto response = authorizationService.signIn(credentials);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/verify-token-validity")
    public ResponseEntity<?> isJwtValid(@RequestParam String jwt) {
        boolean isValid = authorizationService.isJwtValid(jwt);
        Map<String, Object> response = Map.of("isValid", isValid);
        return ResponseEntity.ok(response);
    }

}
