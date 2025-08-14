package org.hl.wirtualnyregalbackend.author;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.dto.AuthorDetailsResponseDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorPageResponseDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponseDto;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author_review.AuthorReviewService;
import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.review.ReviewStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorReviewService reviewService;

    public AuthorResponseDto createAuthor(AuthorMutationDto authorDto) {
        Author author = createAuthorEntity(authorDto);
        return AuthorMapper.toAuthorResponseDto(author);
    }

    public AuthorDetailsResponseDto findAuthorDetailsById(Long authorId) {
        Author author = findAuthorEntityById(authorId);
        ReviewStatistics stats = reviewService.getAuthorReviewStats(authorId);
        AuthorReview review = reviewService.findAuthorReviewEntityById(authorId);
        return AuthorMapper.toAuthorDetailsResponseDto(author, stats, review);
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
            return findAuthorEntityById(id);
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

    private Author findAuthorEntityById(Long id) {
        Optional<Author> authorOpt = id != null ? authorRepository.findById(id) : Optional.empty();
        return authorOpt.orElseThrow(() -> new EntityNotFoundException("Author with id='%d' not found".formatted(id)));
    }

}
