package org.hl.wirtualnyregalbackend.bookshelf.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.security.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "bookshelf")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookshelf extends BaseEntity {

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private BookshelfType type;

    @Column
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "bookshelf", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<BookshelfBook> bookshelfBooks = new ArrayList<>();

    public Bookshelf(String name, BookshelfType type, String description, User user, List<BookshelfBook> bookshelfBooks) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.user = user;
        setBookshelfBooks(bookshelfBooks);
    }

    public BookshelfBook getBookshelfBookById(Long bookshelfBookId) {
        return bookshelfBooks.stream()
            .filter(bookshelfBook -> bookshelfBook.getId().equals(bookshelfBookId))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("BookshelfBook with id='%d' not found".formatted(bookshelfBookId)));
    }

    public void addBookshelfBook(BookshelfBook book) {
        bookshelfBooks.forEach(bookshelfBook -> {
            Book b = bookshelfBook.getBook();
            if (b.equals(book.getBook())) {
                throw new InvalidRequestException("Book is already in the bookshelf");
            }
        });

        bookshelfBooks.add(book);
    }

    public void removeBookshelfBook(Long bookshelfBookId) {
        boolean isSuccess = bookshelfBooks.removeIf(bookshelfBook -> bookshelfBook.getId().equals(bookshelfBookId));
        if (!isSuccess) {
            throw new InvalidRequestException("Bookshelf book id(%s) not found".formatted(bookshelfBookId));
        }
    }

    public List<BookshelfBook> getBookshelfBooks() {
        return Collections.unmodifiableList(bookshelfBooks);
    }

    public void setBookshelfBooks(List<BookshelfBook> bookshelfBooks) {
        this.bookshelfBooks = bookshelfBooks;
        if (bookshelfBooks != null) {
            bookshelfBooks.forEach(bookshelfBook -> bookshelfBook.setBookshelf(this));
        }
    }


}
