package org.hl.wirtualnyregalbackend.author.dto;

public record AuthorResponseDto(
    Long id,
    String fullName,
    String photoUrl
) {
}
