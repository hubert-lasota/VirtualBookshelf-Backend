package org.hl.wirtualnyregalbackend.book.event;

import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.security.entity.User;

public record BookFoundEvent(Book book, User user) {
}
