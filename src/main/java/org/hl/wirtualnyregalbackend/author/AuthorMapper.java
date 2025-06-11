package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author.entity.AuthorProfilePicture;

public class AuthorMapper {

    private AuthorMapper() {
    }

    public static AuthorMutationDto toAuthorMutationDto(Author author) {
        return new AuthorMutationDto(
            author.getFullName(),
            author.getAuthorProfilePicture().getUrl(),
            author.getDescription()
        );
    }

    public static Author toAuthor(AuthorMutationDto authorDto) {
        AuthorProfilePicture authorProfilePicture = new AuthorProfilePicture(authorDto.photoUrl());
        return new Author(authorDto.fullName(), authorDto.description(), authorProfilePicture, null);
    }

}
