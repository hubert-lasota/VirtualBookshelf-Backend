package org.hl.wirtualnyregalbackend.book.event;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;

public record BookFoundEvent(Book book, User user) {
}
