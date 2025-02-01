package org.hl.wirtualnyregalbackend.application.author;

import org.hl.wirtualnyregalbackend.application.common.ActionType;
import org.hl.wirtualnyregalbackend.application.common.InvalidRequestException;
import org.hl.wirtualnyregalbackend.infrastructure.author.AuthorRepository;
import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorRequest;
import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Value("${book.client.id-prefix}")
    private String EXTERNAL_API_ID_PREFIX;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Long createAuthor(AuthorRequest authorRequest) {
        String fullName = authorRequest.fullName();
        boolean exists = authorRepository.exitsByFullName(fullName);
        if (exists) {
            throw new InvalidRequestException(null, ActionType.CREATE, "Author with this full name = %s already exists"
                    .formatted(fullName));
        }
        Author author = AuthorMapper.toAuthor(authorRequest);
        return authorRepository.save(author).getId();
    }

    public void createAuthor(AuthorResponse authorResponse) {
        String id = authorResponse.id();
        if(id.startsWith(EXTERNAL_API_ID_PREFIX)) {
            boolean exists = authorRepository.exitsByExternalApiId(id);
            if(!exists) {
                Author author = AuthorMapper.toAuthor(authorResponse);
                authorRepository.save(author);
            }
        }
    }

    public Set<Author> findAndCreateAuthors(Collection<String> authorNames) {
        List<Author> existingAuthors = authorRepository.findByFullNamesIgnoreCase(authorNames);
        List<String> existingNames = existingAuthors
                .stream().map(Author::getFullName)
                .toList();

        List<Author> newAuthors = authorNames.stream()
                .filter(name -> !existingNames.contains(name))
                .map(Author::new)
                .toList();

        newAuthors = authorRepository.saveAll(newAuthors);

        Set<Author> authors = new HashSet<>(existingAuthors);
        authors.addAll(newAuthors);
        return authors;
    }

}
