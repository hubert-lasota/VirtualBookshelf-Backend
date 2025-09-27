package org.hl.wirtualnyregalbackend.book;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_review.BookReviewHelper;
import org.hl.wirtualnyregalbackend.book_review.event.BookReviewCreatedOrUpdatedEvent;
import org.hl.wirtualnyregalbackend.book_review.event.BookReviewDeletedEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Transactional
@Async
@AllArgsConstructor
class BookEventListener {

    private final BookService bookService;
    private final BookReviewHelper bookReviewHelper;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEvent(BookReviewCreatedOrUpdatedEvent event) {
        Book book = bookService.findBookById(event.bookId());
        Double average = bookReviewHelper.calculateBookRatingAverage(book);
        book.setAverageRating(average);
        book.incrementTotalReviews();
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleEvent(BookReviewDeletedEvent event) {
        Book book = bookService.findBookById(event.bookId());
        Double average = bookReviewHelper.calculateBookRatingAverage(book);
        book.setAverageRating(average);
        book.decrementTotalReviews();
    }

}
