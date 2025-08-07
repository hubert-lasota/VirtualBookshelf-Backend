package org.hl.wirtualnyregalbackend.auth;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.dto.UserCredentialsDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/auth")
@AllArgsConstructor
class AuthorizationController {

    private final AuthorizationService authorizationService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated(CreateGroup.class) @RequestBody UserCredentialsDto credentials) {
        var response = authorizationService.registerUser(credentials);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody UserCredentialsDto credentials) {
        var response = authorizationService.signIn(credentials);
        return ResponseEntity.ok(response);
    }

    // TODO raczej do usuniecia
    @GetMapping("/verify-jwt-validity")
    public ResponseEntity<?> verifyJwt(@RequestParam String jwt) {
        boolean isValid = authorizationService.isJwtValid(jwt);
        Map<String, Object> response = Map.of("isValid", isValid);
        return ResponseEntity.ok(response);
    }

}
