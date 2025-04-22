package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.author.model.AuthorPhoto;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;

public class AuthorMapper {

    private AuthorMapper() {
    }

    public static AuthorDto toAuthorDto(Author author) {
        return new AuthorDto(
            author.getId(),
            author.getFullName(),
            author.getPhoto().getPhotoUrl(),
            author.getDescription()
        );
    }

    public static Author toAuthor(AuthorDto authorDto) {
        AuthorPhoto authorPhoto = new AuthorPhoto(authorDto.photoUrl());
        return new Author(authorDto.fullName(), authorDto.description(), authorPhoto, null);
    }

}
