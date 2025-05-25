package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDto createAuthor(AuthorDto authorDto) {
        Author author = createAuthorEntity(authorDto);
        return AuthorMapper.toAuthorDto(author);
    }

    public Author findOrCreateAuthor(AuthorDto authorDto) {
        Optional<Author> authorOpt = authorRepository.findById(authorDto.id());
        return authorOpt.orElseGet(() -> createAuthorEntity(authorDto));
    }

    private Author createAuthorEntity(AuthorDto authorDto) {
        String fullName = authorDto.fullName();
        boolean exists = authorRepository.existsByFullName(fullName);
        if (exists) {
            throw new InvalidRequestException("Author with this full name = %s already exists".formatted(fullName));
        }
        Author author = AuthorMapper.toAuthor(authorDto);
        return authorRepository.save(author);
    }
}
