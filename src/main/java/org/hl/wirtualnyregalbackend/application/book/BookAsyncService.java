package org.hl.wirtualnyregalbackend.application.book;

import org.hl.wirtualnyregalbackend.application.author.AuthorService;
import org.hl.wirtualnyregalbackend.infrastructure.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.infrastructure.book.BookRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BookAsyncService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    public BookAsyncService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    @Async
    public void saveBookAsync(Book book) {
        bookRepository.save(book);
    }

    @Async
    public void saveBookAsync(Book book, Collection<AuthorResponse> authors) {
        authors.forEach(authorService::saveAuthor);
        bookRepository.save(book);
    }

}
