package org.hl.wirtualnyregalbackend.book;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Component
public class BookHelper {

    private final BookRepository bookRepository;

    public Book findBookById(Long id) throws EntityNotFoundException {
        Optional<Book> bookOpt = id != null ? bookRepository.findById(id) : Optional.empty();
        return bookOpt.orElseThrow(() -> new EntityNotFoundException("Book with id = '%d' not found.".formatted(id)));
    }

}
