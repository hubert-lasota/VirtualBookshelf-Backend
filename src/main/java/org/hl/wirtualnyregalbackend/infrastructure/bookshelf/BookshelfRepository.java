package org.hl.wirtualnyregalbackend.infrastructure.bookshelf;


import org.hl.wirtualnyregalbackend.application.bookshelf.Bookshelf;

import java.util.List;

public interface BookshelfRepository {

    Bookshelf save(Bookshelf entity);

    void saveAll(List<Bookshelf> entities);

    Bookshelf findWithBooksById(Long id);

    List<Bookshelf> findByUserId(Long userId);

    boolean isUserBookshelfAuthor(Long bookshelfId, Long userId);

}
