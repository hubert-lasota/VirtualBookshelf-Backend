package org.hl.wirtualnyregalbackend.author_review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AuthorReviewHelper {

    private final AuthorReviewRepository authorReviewRepository;

    @Nullable
    public AuthorReview findAuthorReviewByAuthorIdAndUserId(Long authorId, Long userId) {
        return authorReviewRepository.findByAuthorIdAndUserId(authorId, userId).orElse(null);
    }

    public Double calculateAuthorRatingAverage(Author author) {
        Double avg = authorReviewRepository.calculateAverageRatingByAuthor(author);
        return avg == null ? 0.0D : avg;
    }

}
