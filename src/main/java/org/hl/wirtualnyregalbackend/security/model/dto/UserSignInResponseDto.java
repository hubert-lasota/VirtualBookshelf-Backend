package org.hl.wirtualnyregalbackend.security.model.dto;

public record UserSignInResponseDto(Long id, String username, String jwt) {
}
