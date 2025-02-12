package org.hl.wirtualnyregalbackend.security;

import org.hl.wirtualnyregalbackend.security.model.dto.LoginRequest;
import org.hl.wirtualnyregalbackend.security.model.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
class AuthorizationController {

    private final AuthorizationService authorizationService;

    AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest request) {
        LoginResponse response = authorizationService.registerUser(request);

        return response != null ?
                ResponseEntity.ok(response) :
                ResponseEntity.badRequest().body(null);
    }

    @PostMapping(value = "/signIn")
    public ResponseEntity<?> signIn(@RequestBody LoginRequest request) {
        LoginResponse response = authorizationService.signIn(request);

        return response != null ?
                ResponseEntity.ok(response) :
                ResponseEntity.badRequest().body(null);
    }

    @GetMapping(value = "/isValid")
    public ResponseEntity<?> isJwtValid(@RequestParam String jwt) {
        boolean isValid = authorizationService.isJwtValid(jwt);

        return ResponseEntity.ok(isValid);
    }

}
