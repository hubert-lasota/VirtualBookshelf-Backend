package org.hl.wirtualnyregalbackend.bookshelf;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.book_review.BookReviewService;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;
import org.hl.wirtualnyregalbackend.bookshelf_book.BookshelfBookService;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookResponseDto;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class BookshelfService {

    private final BookshelfRepository bookshelfRepository;
    private final BookshelfHelper bookshelfHelper;
    private final BookshelfBookService bookshelfBookService;
    private final BookReviewService bookReviewService;


    public void addDefaultBookshelvesToUser(User user) {
        List<Bookshelf> bookshelvesToSave = new ArrayList<>();
        Locale locale = LocaleContextHolder.getLocale();
        String toReadName;
        String readingName;
        String readName;
        if (locale.getLanguage().equals("pl")) {
            toReadName = "Do przeczytania";
            readingName = "W trakcie czytania";
            readName = "Przeczytane";
        } else {
            toReadName = "To read";
            readingName = "Reading";
            readName = "Read";
        }
        bookshelvesToSave.add(new Bookshelf(toReadName, BookshelfType.TO_READ, "", user));
        bookshelvesToSave.add(new Bookshelf(readingName, BookshelfType.READING, "", user));
        bookshelvesToSave.add(new Bookshelf(readName, BookshelfType.READ, "", user));
        bookshelfRepository.saveAll(bookshelvesToSave);
    }


    public BookshelfResponseDto createBookshelf(BookshelfMutationDto bookshelfDto, User user) {
        Bookshelf bookshelf = BookshelfMapper.toBookshelf(bookshelfDto, user);
        bookshelfRepository.save(bookshelf);
        return mapToBookshelfResponseDto(bookshelf);
    }


    public BookshelfResponseDto updateBookshelf(Long id, BookshelfMutationDto bookshelfDto) {
        Bookshelf bookshelf = bookshelfHelper.findBookshelfById(id);

        String name = bookshelfDto.name();
        if (name != null) {
            bookshelf.setName(name);
        }

        BookshelfType type = bookshelfDto.type();
        if (type != null) {
            bookshelf.setType(type);
        }

        String description = bookshelfDto.description();
        if (description != null) {
            bookshelf.setDescription(description);
        }

        bookshelfRepository.save(bookshelf);
        return mapToBookshelfResponseDto(bookshelf);
    }

    public void deleteBookshelf(Long id) {
        bookshelfRepository.deleteById(id);
    }

    public List<BookshelfResponseDto> findUserBookshelves(Long userId) {
        return bookshelfRepository
            .findByUserId(userId)
            .stream()
            .map(this::mapToBookshelfResponseDto)
            .toList();
    }

    public boolean isUserBookshelfAuthor(Long bookshelfId, Long userId) {
        return bookshelfRepository.isUserBookshelfAuthor(bookshelfId, userId);
    }


    private BookshelfResponseDto mapToBookshelfResponseDto(Bookshelf bookshelf) {
        List<BookshelfBookResponseDto> books = bookshelfBookService.findBookshelfBooks(bookshelf.getId());
        return BookshelfMapper.toBookshelfResponseDto(bookshelf, books);
    }

}
