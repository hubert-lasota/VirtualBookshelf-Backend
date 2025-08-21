package org.hl.wirtualnyregalbackend.author;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author.exception.AuthorNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class AuthorHelper {

    private final AuthorRepository authorRepository;

    public Author findAuthorById(Long id) throws AuthorNotFoundException {
        Optional<Author> authorOpt = id != null ? authorRepository.findById(id) : Optional.empty();
        return authorOpt.orElseThrow(() -> new AuthorNotFoundException(id));
    }

}
