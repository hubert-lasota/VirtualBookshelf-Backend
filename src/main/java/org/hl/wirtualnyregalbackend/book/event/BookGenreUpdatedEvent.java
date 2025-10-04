package org.hl.wirtualnyregalbackend.book.event;

import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;

import java.util.Set;

public record BookGenreUpdatedEvent(
    Long bookId,
    Set<Genre> oldGenres,
    Set<Genre> newGenres
) {

    public static BookGenreUpdatedEvent fromCreatedBook(Book book) {
        return new BookGenreUpdatedEvent(book.getId(), Set.of(), book.getGenres());
    }

    public static BookGenreUpdatedEvent fromUpdatedBook(Book book, Set<Genre> newGenres) {
        return new BookGenreUpdatedEvent(book.getId(), book.getGenres(), newGenres);
    }

}
