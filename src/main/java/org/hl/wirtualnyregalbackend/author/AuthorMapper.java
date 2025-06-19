package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponseDto;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author.entity.AuthorProfilePicture;

public class AuthorMapper {

    private AuthorMapper() {
    }

    public static AuthorMutationDto toAuthorMutationDto(Author author) {
        AuthorProfilePicture picture = author.getAuthorProfilePicture();
        String url = picture != null ? picture.getUrl() : null;
        return new AuthorMutationDto(
            author.getFullName(),
            url,
            author.getDescription()
        );
    }

    public static AuthorResponseDto toAuthorResponseDto(Author author) {
        AuthorMutationDto dto = toAuthorMutationDto(author);
        return new AuthorResponseDto(author.getId(), dto, author.getCreatedAt(), author.getUpdatedAt());
    }

    public static Author toAuthor(AuthorMutationDto authorDto) {
        String photoUrl = authorDto.getPhotoUrl();
        AuthorProfilePicture authorProfilePicture = photoUrl != null ? new AuthorProfilePicture(authorDto.getPhotoUrl()) : null;
        return new Author(authorDto.getFullName(), authorDto.getDescription(), authorProfilePicture, null);
    }

}
