package org.hl.wirtualnyregalbackend.book.event;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;

public record BookFoundEvent(Long bookId, Long userId) {

    public static BookFoundEvent of(Book book, User user) {
        return new BookFoundEvent(book.getId(), user.getId());
    }

}
