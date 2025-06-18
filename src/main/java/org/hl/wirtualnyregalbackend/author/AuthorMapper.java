package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponseDto;
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

    public static AuthorResponseDto toAuthorResponseDto(Author author) {
        AuthorMutationDto dto = toAuthorMutationDto(author);
        return new AuthorResponseDto(author.getId(), dto, author.getCreatedAt(), author.getUpdatedAt());
    }

    public static Author toAuthor(AuthorMutationDto authorDto) {
        AuthorProfilePicture authorProfilePicture = new AuthorProfilePicture(authorDto.getPhotoUrl());
        return new Author(authorDto.getFullName(), authorDto.getDescription(), authorProfilePicture, null);
    }

}
