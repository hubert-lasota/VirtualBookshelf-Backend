package org.hl.wirtualnyregalbackend.bookshelf;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfRequest;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponse;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class BookshelfCommandService {

    private final BookshelfRepository repository;
    private final BookshelfQueryService query;


    @Transactional
    public void addDefaultBookshelvesToUser(User user) {
        List<Bookshelf> bookshelvesToSave = new ArrayList<>();
        boolean isPl = LocaleContextHolder.getLocale().getLanguage().equals("pl");
        String toReadName = isPl ? "Do przeczytania" : "To read";
        String readingName = isPl ? "W trakcie czytania" : "Reading";
        String readName = isPl ? "Przeczytane" : "Read";
        bookshelvesToSave.add(new Bookshelf(toReadName, BookshelfType.TO_READ, "", user));
        bookshelvesToSave.add(new Bookshelf(readingName, BookshelfType.READING, "", user));
        bookshelvesToSave.add(new Bookshelf(readName, BookshelfType.READ, "", user));
        repository.saveAll(bookshelvesToSave);
        log.info("Added default bookshelves: {}", bookshelvesToSave);
    }


    public BookshelfResponse createBookshelf(BookshelfRequest bookshelfRequest, User user) {
        Bookshelf bookshelf = BookshelfMapper.toBookshelf(bookshelfRequest, user);
        repository.save(bookshelf);
        log.info("Created bookshelf: {}", bookshelf);
        return BookshelfMapper.toBookshelfResponse(bookshelf);
    }


    public BookshelfResponse updateBookshelf(Long id, BookshelfRequest bookshelfRequest) {
        Bookshelf bookshelf = query.findBookshelfById(id);
        log.info("Updating bookshelf: {} by request: {}", bookshelf, bookshelfRequest);
        String name = bookshelfRequest.name();
        if (name != null) {
            bookshelf.setName(name);
        }

        BookshelfType type = bookshelfRequest.type();
        if (type != null) {
            bookshelf.setType(type);
        }

        String description = bookshelfRequest.description();
        if (description != null) {
            bookshelf.setDescription(description);
        }

        repository.save(bookshelf);
        log.info("Updated bookshelf: {}", bookshelf);
        return BookshelfMapper.toBookshelfResponse(bookshelf);
    }

    public void deleteBookshelf(Long id) {
        log.info("Deleting bookshelf with ID: {}", id);
        repository.deleteById(id);
    }

}
