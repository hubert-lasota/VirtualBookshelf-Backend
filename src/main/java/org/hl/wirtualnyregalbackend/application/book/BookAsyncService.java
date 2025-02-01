package org.hl.wirtualnyregalbackend.application.book;

import org.hl.wirtualnyregalbackend.application.author.AuthorService;
import org.hl.wirtualnyregalbackend.application.common.InvalidRequestException;
import org.hl.wirtualnyregalbackend.application.publisher.Publisher;
import org.hl.wirtualnyregalbackend.application.publisher.PublisherService;
import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.infrastructure.book.BookRepository;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BookAsyncService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final PublisherService publisherService;

    public BookAsyncService(BookRepository bookRepository, AuthorService authorService, PublisherService publisherService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.publisherService = publisherService;
    }

    @Async
    public void saveBookAsync(Book book) {
        bookRepository.save(book);
    }

    @Async
    public void saveBookAsync(Book book, Collection<AuthorResponse> authors, @Nullable Collection<String> publishers) {
        authors.forEach(authorService::createAuthor);
        if(publishers != null) {
            publishers.forEach(publisher -> {
                try {
                    Publisher createdPublisher = publisherService.createPublisher(publisher);
                    book.addPublisher(createdPublisher);
                } catch (InvalidRequestException e) {
                    // ignore
                }
            });
        }

        bookRepository.save(book);
    }

}
