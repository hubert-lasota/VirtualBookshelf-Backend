package org.hl.wirtualnyregalbackend.infrastructure.book;

import org.hl.wirtualnyregalbackend.application.book.BookGenre;

import java.util.Collection;
import java.util.List;

public interface BookGenreRepository {

    List<BookGenre> saveAll(List<BookGenre> bookGenres);

    List<BookGenre> findByNamesIgnoreCase(Collection<String> names);

}
