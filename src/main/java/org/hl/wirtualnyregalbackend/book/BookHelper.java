package org.hl.wirtualnyregalbackend.book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book.exception.BookNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Component
@Slf4j
public class BookHelper {

    private final BookRepository bookRepository;

    public Book findBookById(Long id) throws BookNotFoundException {
        Optional<Book> bookOpt = id != null ? bookRepository.findById(id) : Optional.empty();
        return bookOpt.orElseThrow(() -> {
            log.warn("Book not found with ID: {}", id);
            return new BookNotFoundException(id);
        });
    }

}
