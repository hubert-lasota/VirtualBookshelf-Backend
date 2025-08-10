package org.hl.wirtualnyregalbackend.author;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorPageResponseDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponseDto;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AuthorService {

    private final AuthorRepository authorRepository;


    public AuthorResponseDto createAuthor(AuthorMutationDto authorDto) {
        Author author = createAuthorEntity(authorDto);
        return AuthorMapper.toAuthorResponseDto(author);
    }

    public AuthorPageResponseDto findAuthors(Boolean availableInBookshelf,
                                             User user,
                                             Pageable pageable) {
        Specification<Author> spec = availableInBookshelf != null
            ? AuthorSpecification.availableInBookshelf(availableInBookshelf, user)
            : Specification.where(null);

        Page<AuthorResponseDto> authorPage = authorRepository
            .findAll(spec, pageable)
            .map(AuthorMapper::toAuthorResponseDto);
        return AuthorPageResponseDto.from(authorPage);
    }

    public Author findOrCreateAuthor(Long id, AuthorMutationDto authorMutationDto) {
        if (id != null) {
            return authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with id='%d' not found".formatted(id)));
        }

        return createAuthorEntity(authorMutationDto);
    }

    private Author createAuthorEntity(AuthorMutationDto authorDto) {
        String fullName = authorDto.getFullName();
        boolean exists = authorRepository.existsByFullName(fullName);
        if (exists) {
            throw new InvalidRequestException("Author with this full name = %s already exists".formatted(fullName));
        }
        Author author = AuthorMapper.toAuthor(authorDto);
        return authorRepository.save(author);
    }

}
