package org.hl.wirtualnyregalbackend.book.dao;

import org.hl.wirtualnyregalbackend.book.model.BookGenre;

import java.util.Collection;
import java.util.List;

public interface BookGenreRepository {

    List<BookGenre> saveAll(List<BookGenre> bookGenres);

    List<BookGenre> findByNamesIgnoreCase(Collection<String> names);

}
