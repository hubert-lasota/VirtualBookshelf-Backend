package org.hl.wirtualnyregalbackend.author.dao;

import org.hl.wirtualnyregalbackend.author.model.Author;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorRepository {

    Author save(Author author);

    Optional<Author> findById(Long id);

    Set<Author> findByIds(List<Long> ids);

    boolean exitsByFullName(String fullName);

}
