package org.hl.wirtualnyregalbackend.bookshelf;


import org.hl.wirtualnyregalbackend.bookshelf.model.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BookshelfRepository extends JpaRepository<Bookshelf, Long> {

    @Query("select b from Bookshelf b  where b.user.id = :userId")
    List<Bookshelf> findByUserId(Long userId);

    @Query("select b from Bookshelf b join Book book where book.id = :bookId and b.user.id = :userId")
    List<Bookshelf> findUserBookshelvesByBookId(Long bookId, Long userId);

    @Query("select count(b.id) > 0 from Bookshelf b where b.id = :bookshelfId and b.user.id = :userId")
    boolean isUserBookshelfAuthor(Long bookshelfId, Long userId);

}
