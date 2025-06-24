package org.hl.wirtualnyregalbackend.bookshelf;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookCoverOrderDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfCreateDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfUpdateDto;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.bookshelf_book.entity.BookshelfBook;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;
import org.hl.wirtualnyregalbackend.bookshelf_book.BookshelfBookService;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookMutationDto;
import org.hl.wirtualnyregalbackend.bookshelf_book.dto.BookshelfBookUpdateDto;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookshelfService {

    private final static Logger log = LoggerFactory.getLogger(BookshelfService.class);

    private final BookshelfRepository bookshelfRepository;
    private final BookshelfHelper bookshelfHelper;
    private final BookshelfBookService bookshelfBookService;


    public void addDefaultBookshelvesToUser(User user) {
        List<Bookshelf> bookshelvesToSave = new ArrayList<>();
        Locale locale = LocaleContextHolder.getLocale();
        String toReadName;
        String readingName;
        String readName;
        if (locale.toLanguageTag().equals("pl-PL")) {
            toReadName = "Do przeczytania";
            readingName = "W trakcie czytania";
            readName = "Przeczytane";
        } else {
            toReadName = "To read";
            readingName = "Reading";
            readName = "Read";
        }
        bookshelvesToSave.add(new Bookshelf(toReadName, BookshelfType.TO_READ, "", user, null));
        bookshelvesToSave.add(new Bookshelf(readingName, BookshelfType.READING, "", user, null));
        bookshelvesToSave.add(new Bookshelf(readName, BookshelfType.READ, "", user, null));
        bookshelfRepository.saveAll(bookshelvesToSave);
    }


    public BookshelfResponseDto createBookshelf(BookshelfCreateDto bookshelfCreateDto,
                                                List<MultipartFile> covers,
                                                List<BookCoverOrderDto> bookCoverOrderDtos,
                                                User user) {
        List<BookshelfBookMutationDto> bookDtos = bookshelfCreateDto.getBooks();
        List<BookshelfBook> bookshelfBooks = IntStream.range(0, bookDtos.size())
            .mapToObj((index) -> {
                Optional<BookCoverOrderDto> orderOpt = bookCoverOrderDtos.stream()
                    .filter(order -> order.bookIndex().equals(index))
                    .findFirst();

                MultipartFile cover = orderOpt.map(bookCoverOrderDto -> covers.get(bookCoverOrderDto.coverIndex())).orElse(null);
                BookshelfBookMutationDto dto = bookDtos.get(index);
                return bookshelfBookService.createBookshelfBookEntity(dto, cover);
            })
            .toList();

        Bookshelf bookshelf = BookshelfMapper.toBookshelf(bookshelfCreateDto, user, bookshelfBooks);
        bookshelfRepository.save(bookshelf);
        Locale locale = LocaleContextHolder.getLocale();
        return BookshelfMapper.toBookshelfResponseDto(bookshelf, locale);
    }


    public BookshelfResponseDto updateBookshelf(Long id,
                                                BookshelfUpdateDto bookshelfDto,
                                                List<MultipartFile> covers,
                                                List<BookCoverOrderDto> bookCoverOrderDtos) {
        Bookshelf bookshelf = bookshelfHelper.findBookshelfById(id);

        String name = bookshelfDto.getName();
        if (name != null) {
            bookshelf.setName(name);
        }

        BookshelfType type = bookshelfDto.getType();
        if (type != null) {
            bookshelf.setType(type);
        }

        String description = bookshelfDto.getDescription();
        if (description != null) {
            bookshelf.setDescription(description);
        }

        List<BookshelfBookUpdateDto> bookDtos = bookshelfDto.getBooks();
        if (bookDtos != null) {
            List<BookshelfBook> books = IntStream.range(0, bookDtos.size())
                .mapToObj(index -> {
                    Optional<BookCoverOrderDto> orderOpt = bookCoverOrderDtos.stream()
                        .filter(order -> order.bookIndex().equals(index))
                        .findFirst();

                    BookshelfBookUpdateDto bookDto = bookDtos.get(index);
                    MultipartFile cover = orderOpt.map(bookCoverOrderDto -> covers.get(bookCoverOrderDto.coverIndex())).orElse(null);
                    Long bookshelfBookId = bookDto.getId();
                    if (bookshelfBookId == null) {
                        return bookshelfBookService.createBookshelfBookEntity(bookDto, cover);
                    }
                    BookshelfBook book = bookshelf.getBookshelfBookById(bookshelfBookId);
                    bookshelfBookService.updateBookshelfBook(book, bookDto);
                    return book;
                })
                .toList();

            bookshelf.setBookshelfBooks(books);
        }

        bookshelfRepository.save(bookshelf);
        Locale locale = LocaleContextHolder.getLocale();
        return BookshelfMapper.toBookshelfResponseDto(bookshelf, locale);
    }

    public void deleteBookshelf(Long id) {
        bookshelfRepository.deleteById(id);
    }

    public List<BookshelfResponseDto> findUserBookshelves(Long userId) {
        List<Bookshelf> bookshelves = bookshelfRepository.findByUserId(userId);
        Locale locale = LocaleContextHolder.getLocale();
        return bookshelves.stream()
            .map(bookshelf -> BookshelfMapper.toBookshelfResponseDto(bookshelf, locale))
            .toList();
    }

    public boolean isUserBookshelfAuthor(Long bookshelfId, Long userId) {
        return bookshelfRepository.isUserBookshelfAuthor(bookshelfId, userId);
    }

}
