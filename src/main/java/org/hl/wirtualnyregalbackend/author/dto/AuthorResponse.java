package org.hl.wirtualnyregalbackend.author.dto;

public record AuthorResponse(
    Long id,
    String fullName,
    String photoUrl
) {
}
