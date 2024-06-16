package org.hl.wirtualnyregalbackend.infrastructure.security;

import org.hl.wirtualnyregalbackend.infrastructure.user.dto.UserRequest;
import org.hl.wirtualnyregalbackend.infrastructure.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthorizationEndpoint {

    private final AuthorizationFacade authorizationFacade;

    public AuthorizationEndpoint(AuthorizationFacade authorizationFacade) {
        this.authorizationFacade = authorizationFacade;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        UserResponse response = authorizationFacade.createUser(request);

        return response != null ?
                ResponseEntity.ok(response) :
                ResponseEntity.badRequest().body(null);
    }

    @PostMapping(value = "/signIn")
    public ResponseEntity<UserResponse> signIn(@RequestBody UserRequest request) {
        UserResponse response = authorizationFacade.signIn(request);

        return response != null ?
                ResponseEntity.ok(response) :
                ResponseEntity.badRequest().body(null);
    }

}
