package org.hl.wirtualnyregalbackend.bookshelf;


import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BookshelfRepository extends JpaRepository<Bookshelf, Long> {

    List<Bookshelf> findByUser(User user);

    @Query("select count(b.id) > 0 from Bookshelf b where b.id = :bookshelfId and b.user.id = :userId")
    boolean isUserBookshelfAuthor(Long bookshelfId, Long userId);

}
