package org.hl.wirtualnyregalbackend.author.model.dto;

public record AuthorResponse(String id, String fullName, String photoUrl, String description) {

    public AuthorResponse {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cannot be null or blank");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name cannot be null or blank");
        }
    }

}
