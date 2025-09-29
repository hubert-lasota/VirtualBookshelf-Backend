package org.hl.wirtualnyregalbackend.author;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.dto.AuthorDetailsResponse;
import org.hl.wirtualnyregalbackend.author.dto.AuthorPageResponse;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author.exception.AuthorNotFoundException;
import org.hl.wirtualnyregalbackend.author_review.AuthorReviewQueryService;
import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@Slf4j
public class AuthorQueryService {

    private final AuthorRepository repository;
    private final AuthorReviewQueryService reviewQuery;


    public Author findAuthorById(Long id) throws AuthorNotFoundException {
        Optional<Author> authorOpt = id != null ? repository.findById(id) : Optional.empty();
        return authorOpt.orElseThrow(() -> {
            log.warn("Author not found with ID: {}", id);
            return new AuthorNotFoundException(id);
        });
    }


    AuthorDetailsResponse findAuthorDetailsById(Long authorId) {
        Author author = findAuthorById(authorId);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AuthorReview review = reviewQuery.findAuthorReviewByAuthorIdAndUserId(authorId, user.getId());
        return AuthorMapper.toAuthorDetailsResponse(author, review);
    }

    AuthorPageResponse findAuthors(Boolean availableInBookshelf,
                                   User user,
                                   Pageable pageable) {
        Specification<Author> spec = availableInBookshelf != null
            ? AuthorSpecification.availableInBookshelf(availableInBookshelf, user)
            : Specification.where(null);

        Page<AuthorResponse> authorPage = repository
            .findAll(spec, pageable)
            .map(AuthorMapper::toAuthorResponse);
        return AuthorPageResponse.from(authorPage);
    }

}
