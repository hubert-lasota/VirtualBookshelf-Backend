package org.hl.wirtualnyregalbackend.author;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author_review.AuthorReviewQueryService;
import org.hl.wirtualnyregalbackend.author_review.event.AuthorReviewCreatedOrUpdatedEvent;
import org.hl.wirtualnyregalbackend.author_review.event.AuthorReviewDeletedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@AllArgsConstructor
@Transactional
@Async
class AuthorEventListener {

    private final AuthorQueryService authorQuery;
    private final AuthorReviewQueryService reviewQuery;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEvent(AuthorReviewCreatedOrUpdatedEvent event) {
        Author author = authorQuery.findAuthorById(event.authorId());
        Double avg = reviewQuery.calculateAuthorRatingAverage(author);
        author.setAverageRating(avg);
        author.incrementTotalReviews();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEvent(AuthorReviewDeletedEvent event) {
        Author author = authorQuery.findAuthorById(event.authorId());
        Double avg = reviewQuery.calculateAuthorRatingAverage(author);
        author.setAverageRating(avg);
        author.decrementTotalReviews();
    }

}
