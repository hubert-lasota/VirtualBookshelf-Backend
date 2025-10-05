package org.hl.wirtualnyregalbackend.author_review;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;
import org.hl.wirtualnyregalbackend.author_review.exception.AuthorReviewNotFoundException;
import org.hl.wirtualnyregalbackend.common.review.ReviewMapper;
import org.hl.wirtualnyregalbackend.common.review.ReviewPageResponse;
import org.hl.wirtualnyregalbackend.common.review.ReviewResponse;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@Slf4j
public class AuthorReviewQueryService {

    private final AuthorReviewRepository repository;


    @Nullable
    public AuthorReview findAuthorReviewByAuthorIdAndUserId(Long authorId, Long userId) {
        return repository.findByAuthorIdAndUserId(authorId, userId).orElse(null);
    }

    public Double calculateAuthorRatingAverage(Author author) {
        Double avg = repository.calculateAverageRatingByAuthor(author);
        return avg == null ? 0.0D : avg;
    }

    public boolean isAuthorReviewOwner(Long authorReviewId, User user) {
        return repository.isAuthor(authorReviewId, user.getId());
    }


    ReviewPageResponse findAuthorReviews(Long authorId, Pageable pageable) {
        Locale locale = LocaleContextHolder.getLocale();
        Page<ReviewResponse> page = repository
            .findByAuthorId(authorId, pageable)
            .map((review) -> ReviewMapper.toReviewResponse(review, locale));
        return ReviewPageResponse.from(page);
    }

    AuthorReview findAuthorReviewById(Long id) throws AuthorReviewNotFoundException {
        Optional<AuthorReview> reviewOpt = id != null ? repository.findById(id) : Optional.empty();
        return reviewOpt.orElseThrow(() -> {
            log.warn("AuthorReview not found with ID: {}", id);
            return new AuthorReviewNotFoundException(id);
        });
    }

}
