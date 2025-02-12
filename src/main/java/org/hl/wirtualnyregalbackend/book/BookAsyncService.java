package org.hl.wirtualnyregalbackend.book;

import org.hl.wirtualnyregalbackend.author.AuthorService;
import org.hl.wirtualnyregalbackend.author.model.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.book.dao.BookRepository;
import org.hl.wirtualnyregalbackend.book.model.Book;
import org.hl.wirtualnyregalbackend.common.InvalidRequestException;
import org.hl.wirtualnyregalbackend.publisher.PublisherService;
import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
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
