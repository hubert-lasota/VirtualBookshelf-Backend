package org.hl.wirtualnyregalbackend.book.event;

import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.security.model.User;

public record BookFoundEvent(Book book, User user) {
}
