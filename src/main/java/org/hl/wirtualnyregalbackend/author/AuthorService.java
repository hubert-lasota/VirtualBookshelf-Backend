package org.hl.wirtualnyregalbackend.author;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.dto.AuthorDetailsResponse;
import org.hl.wirtualnyregalbackend.author.dto.AuthorPageResponse;
import org.hl.wirtualnyregalbackend.author.dto.AuthorRequest;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author.exception.AuthorNotFoundException;
import org.hl.wirtualnyregalbackend.author_review.AuthorReviewService;
import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;
import org.hl.wirtualnyregalbackend.common.review.ReviewStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorReviewService reviewService;

    public AuthorResponse createAuthor(AuthorRequest authorRequest) {
        log.info("Creating new author: {}", authorRequest);
        Author author = createAuthorEntity(authorRequest);
        log.info("Author created with ID: {}", author.getId());
        return AuthorMapper.toAuthorResponse(author);
    }

    public AuthorDetailsResponse findAuthorDetailsById(Long authorId) {
        Author author = findAuthorById(authorId);
        ReviewStatistics stats = reviewService.getAuthorReviewStats(authorId);
        AuthorReview review = reviewService.findOptionalAuthorReviewById(authorId).orElse(null);
        return AuthorMapper.toAuthorDetailsResponse(author, stats, review);
    }

    public AuthorPageResponse findAuthors(Boolean availableInBookshelf,
                                          User user,
                                          Pageable pageable) {
        Specification<Author> spec = availableInBookshelf != null
            ? AuthorSpecification.availableInBookshelf(availableInBookshelf, user)
            : Specification.where(null);

        Page<AuthorResponse> authorPage = authorRepository
            .findAll(spec, pageable)
            .map(AuthorMapper::toAuthorResponse);
        return AuthorPageResponse.from(authorPage);
    }

    public Author findOrCreateAuthor(Long id, AuthorRequest authorRequest) {
        if (id != null) {
            return findAuthorById(id);
        }

        return createAuthorEntity(authorRequest);
    }

    private Author createAuthorEntity(AuthorRequest authorRequest) {
        Author author = AuthorMapper.toAuthor(authorRequest);
        return authorRepository.save(author);
    }

    private Author findAuthorById(Long id) throws AuthorNotFoundException {
        Optional<Author> authorOpt = id != null ? authorRepository.findById(id) : Optional.empty();
        return authorOpt.orElseThrow(() -> {
            log.warn("Author not found with ID: {}", id);
            return new AuthorNotFoundException(id);
        });
    }

}
