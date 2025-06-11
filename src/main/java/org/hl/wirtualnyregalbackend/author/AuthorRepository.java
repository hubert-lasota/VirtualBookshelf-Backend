package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("select a from Author a where a.id in (:ids)")
    Set<Author> findByIds(List<Long> ids);

    boolean existsByFullName(String fullName);

}
