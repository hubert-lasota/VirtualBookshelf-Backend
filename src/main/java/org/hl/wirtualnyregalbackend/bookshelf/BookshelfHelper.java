package org.hl.wirtualnyregalbackend.bookshelf;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class BookshelfHelper {

    private final BookshelfRepository bookshelfRepository;


    public Bookshelf findBookshelfById(Long bookshelfId) throws EntityNotFoundException {
        Optional<Bookshelf> bookshelfOpt = bookshelfId != null ? bookshelfRepository.findById(bookshelfId) : Optional.empty();
        return bookshelfOpt.orElseThrow(() -> new EntityNotFoundException("Not found Bookshelf with id = %d".formatted(bookshelfId)));
    }


}
