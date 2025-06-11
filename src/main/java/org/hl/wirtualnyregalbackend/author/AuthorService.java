package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorMutationDto createAuthor(AuthorMutationDto authorDto) {
        Author author = createAuthorEntity(authorDto);
        return AuthorMapper.toAuthorMutationDto(author);
    }

    public Author findOrCreateAuthor(Long id, AuthorMutationDto authorMutationDto) {
        if (id != null) {
            return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id='%d' not found".formatted(id)));
        }

        return createAuthorEntity(authorMutationDto);
    }

    private Author createAuthorEntity(AuthorMutationDto authorDto) {
        String fullName = authorDto.fullName();
        boolean exists = authorRepository.existsByFullName(fullName);
        if (exists) {
            throw new InvalidRequestException("Author with this full name = %s already exists".formatted(fullName));
        }
        Author author = AuthorMapper.toAuthor(authorDto);
        return authorRepository.save(author);
    }
}
