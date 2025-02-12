package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorRequest;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorResponse;

public class AuthorMapper {

    private AuthorMapper() { }

    public static AuthorResponse toAuthorResponse(Author author) {
        String id = author.getId() == null ? author.getExternalApiId() : author.getId().toString();
        return new AuthorResponse(id, author.getFullName(), null, null);
    }

    public static Author toAuthor(AuthorResponse authorResponse) {
        return new Author(authorResponse.fullName(), authorResponse.id());
    }

    public static Author toAuthor(AuthorRequest authorRequest) {
        return new Author(authorRequest.fullName());
    }

}
