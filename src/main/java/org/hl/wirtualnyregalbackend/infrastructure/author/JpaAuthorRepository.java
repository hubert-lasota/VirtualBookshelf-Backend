package org.hl.wirtualnyregalbackend.infrastructure.author;

import org.hl.wirtualnyregalbackend.application.author.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
    public boolean exitsByExternalApiId(String externalApiId) {
        return authorRepository.existsByExternalApiId(externalApiId);
    }

    @Override
    public boolean exitsByFullName(String fullName) {
        return authorRepository.existsByFullName(fullName);
    }
}

@Repository
interface SpringJpaAuthorRepository extends JpaRepository<Author, Long> {

    boolean existsByExternalApiId(String externalApiId);

    boolean existsByFullName(String fullName);

}
