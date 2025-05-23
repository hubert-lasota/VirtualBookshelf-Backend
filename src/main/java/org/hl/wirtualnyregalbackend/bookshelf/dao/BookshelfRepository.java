package org.hl.wirtualnyregalbackend.bookshelf.dao;


import org.hl.wirtualnyregalbackend.bookshelf.model.Bookshelf;

import java.util.List;

public interface BookshelfRepository {

    Bookshelf save(Bookshelf entity);

    void saveAll(List<Bookshelf> entities);

    Bookshelf findById(Long id);

    List<Bookshelf> findByUserId(Long userId);

    List<Bookshelf> findUserBookshelvesByBookId(Long bookId, Long userId);

    boolean isUserBookshelfAuthor(Long bookshelfId, Long userId);

}
