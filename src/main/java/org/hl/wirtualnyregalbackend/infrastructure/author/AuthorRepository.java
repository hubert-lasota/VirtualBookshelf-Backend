package org.hl.wirtualnyregalbackend.infrastructure.author;

import org.hl.wirtualnyregalbackend.application.author.Author;

import java.util.Collection;
import java.util.List;

public interface AuthorRepository {

    Author save(Author author);

    List<Author> saveAll(Collection<Author> authors);

    List<Author> findByFullNamesIgnoreCase(Collection<String> fullNames);

    boolean exitsByExternalApiId(String externalApiId);

    boolean exitsByFullName(String fullName);
}
