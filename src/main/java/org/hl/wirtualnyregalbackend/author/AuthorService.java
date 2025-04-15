package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.dao.AuthorRepository;
import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorDto;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDto createAuthor(AuthorDto authorDto) {
        String fullName = authorDto.fullName();
        boolean exists = authorRepository.exitsByFullName(fullName);
        if (exists) {
            throw new InvalidRequestException("Author with this full name = %s already exists".formatted(fullName));
        }
        Author author = AuthorMapper.toAuthor(authorDto);
        authorRepository.save(author);
        return AuthorMapper.toAuthorDto(author);
    }

}
