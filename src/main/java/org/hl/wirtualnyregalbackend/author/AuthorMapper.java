package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.author.model.AuthorProfilePicture;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;

public class AuthorMapper {

    private AuthorMapper() {
    }

    public static AuthorDto toAuthorDto(Author author) {
        return new AuthorDto(
            author.getId(),
            author.getFullName(),
            author.getAuthorProfilePicture().getUrl(),
            author.getDescription()
        );
    }

    public static Author toAuthor(AuthorDto authorDto) {
        AuthorProfilePicture authorProfilePicture = new AuthorProfilePicture(authorDto.photoUrl());
        return new Author(authorDto.fullName(), authorDto.description(), authorProfilePicture, null);
    }

}
