package org.hl.wirtualnyregalbackend.author.dao;

import org.hl.wirtualnyregalbackend.author.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
class JpaAuthorRepository implements AuthorRepository {

    private final SpringJpaAuthorRepository authorRepository;

    public JpaAuthorRepository(SpringJpaAuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public boolean exitsByFullName(String fullName) {
        return authorRepository.existsByFullName(fullName);
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Set<Author> findByIds(List<Long> ids) {
        return authorRepository.findByIds(ids);
    }

}

@Repository
interface SpringJpaAuthorRepository extends JpaRepository<Author, Long> {

    @Query("select a from Author a where a.id in (:ids)")
    Set<Author> findByIds(List<Long> ids);

    boolean existsByFullName(String fullName);

}
