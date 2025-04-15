package org.hl.wirtualnyregalbackend.author.dao;

import org.hl.wirtualnyregalbackend.author.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

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
    public List<Author> saveAll(Collection<Author> authors) {
        return authorRepository.saveAll(authors);
    }

    @Override
    public List<Author> findByFullNamesIgnoreCase(Collection<String> fullNames) {
        return authorRepository.findByFullNamesIgnoreCase(fullNames);
    }

    @Override
    public boolean exitsByFullName(String fullName) {
        return authorRepository.existsByFullName(fullName);
    }
}

@Repository
interface SpringJpaAuthorRepository extends JpaRepository<Author, Long> {

    @Query("select a from Author a where lower(a.fullName) in :fullNames")
    List<Author> findByFullNamesIgnoreCase(Collection<String> fullNames);

    boolean existsByFullName(String fullName);

}
