package org.hl.wirtualnyregalbackend.author.model.dto;

public record AuthorResponse(
    Long id,
    String fullName,
    String photoUrl,
    String description
) { }
