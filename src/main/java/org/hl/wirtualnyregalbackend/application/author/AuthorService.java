package org.hl.wirtualnyregalbackend.application.author;

import org.hl.wirtualnyregalbackend.application.author.exception.IllegalAuthorOperationException;
import org.hl.wirtualnyregalbackend.infrastructure.author.AuthorRepository;
import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorRequest;
import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Value("${book.client.id-prefix}")
    private String EXTERNAL_API_ID_PREFIX;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Long saveAuthor(AuthorRequest authorRequest) {
        String fullName = authorRequest.fullName();
        boolean exists = authorRepository.exitsByFullName(fullName);
        if (exists) {
            throw new IllegalAuthorOperationException(null, "create", "Author with this full name = %s already exists"
                    .formatted(fullName));
        }
        Author author = AuthorMapper.toAuthor(authorRequest);
        return authorRepository.save(author).getId();
    }

    public void saveAuthor(AuthorResponse authorResponse) {
        String id = authorResponse.id();
        if(id.startsWith(EXTERNAL_API_ID_PREFIX)) {
            boolean exists = authorRepository.exitsByExternalApiId(id);
            if(!exists) {
                Author author = AuthorMapper.toAuthor(authorResponse);
                authorRepository.save(author);
            }
        }
    }

}
