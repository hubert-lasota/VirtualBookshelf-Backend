package org.hl.wirtualnyregalbackend.book_review;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_review.dto.BookReviewCreateDto;
import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;

public class BookReviewMapper {

    private BookReviewMapper() {
    }

    public static BookReview toBookReview(BookReviewCreateDto reviewDto, Book book, User user) {
        return new BookReview(reviewDto.getRating(), reviewDto.getContent(), user, book);
    }
}
