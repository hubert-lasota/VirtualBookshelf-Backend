package org.hl.wirtualnyregalbackend.infrastructure.author;

import org.hl.wirtualnyregalbackend.application.author.Author;

public interface AuthorRepository {

    Author save(Author author);

    boolean exitsByExternalApiId(String externalApiId);

    boolean exitsByFullName(String fullName);
}
