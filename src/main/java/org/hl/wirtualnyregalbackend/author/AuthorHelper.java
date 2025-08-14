package org.hl.wirtualnyregalbackend.author;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class AuthorHelper {

    private final AuthorRepository authorRepository;

    public Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Author with id = '%d' not found.".formatted(id)));
    }

}
