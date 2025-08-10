package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.dto.AuthorDetailsResponseDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponseDto;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author.entity.AuthorProfilePicture;

public class AuthorMapper {

    private AuthorMapper() {
    }

    public static AuthorResponseDto toAuthorResponseDto(Author author) {
        return new AuthorResponseDto(
            author.getId(),
            author.getFullName(),
            getPhotoUrl(author)
        );
    }

    public static AuthorDetailsResponseDto toAuthorDetailsResponseDto(Author author) {
        return new AuthorDetailsResponseDto(
            author.getId(),
            author.getFullName(),
            getPhotoUrl(author),
            author.getDescription(),
            author.getCreatedAt(),
            author.getUpdatedAt()
        );
    }

    private static String getPhotoUrl(Author author) {
        AuthorProfilePicture profilePicture = author.getAuthorProfilePicture();
        return profilePicture != null ? profilePicture.getUrl() : null;
    }

    public static Author toAuthor(AuthorMutationDto authorDto) {
        String photoUrl = authorDto.getPhotoUrl();
        AuthorProfilePicture authorProfilePicture = photoUrl != null ? new AuthorProfilePicture(authorDto.getPhotoUrl()) : null;
        return new Author(authorDto.getFullName(), authorDto.getDescription(), authorProfilePicture);
    }

}
