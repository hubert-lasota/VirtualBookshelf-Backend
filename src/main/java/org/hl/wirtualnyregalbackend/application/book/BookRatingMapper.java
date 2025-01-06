package org.hl.wirtualnyregalbackend.application.book;

import org.hl.wirtualnyregalbackend.application.user.UserMapper;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookRatingRequest;
import org.hl.wirtualnyregalbackend.infrastructure.book.dto.BookRatingResponse;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;
import org.hl.wirtualnyregalbackend.infrastructure.user.dto.UserHeaderResponse;

public class BookRatingMapper {

    private BookRatingMapper() { }

    public static BookRating toBookRating(BookRatingRequest bookRatingRequest, User user, Book book) {
        return new BookRating(
                bookRatingRequest.rating(),
                bookRatingRequest.ratingJustification(),
                user,
                book
        );
    }

    public static BookRatingResponse toBookRatingResponse(BookRating bookRating) {
        UserHeaderResponse user = UserMapper.toUserHeaderResponse(bookRating.getUser());
        return new BookRatingResponse(
                bookRating.getId(),
                user,
                bookRating.getRating(),
                bookRating.getRatingJustification()
        );
    }
}
