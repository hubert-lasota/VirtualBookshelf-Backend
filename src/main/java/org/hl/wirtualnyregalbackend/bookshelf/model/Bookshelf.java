package org.hl.wirtualnyregalbackend.bookshelf.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bookshelf")
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "bookshelf_book_bookshelf",
        joinColumns = @JoinColumn(name = "bookshelf_id"),
        inverseJoinColumns = @JoinColumn(name = "bookshelf_book_id"))
    private Set<BookshelfBook> bookshelfBooks = new HashSet<>();

    protected Bookshelf() {
    }

    public Bookshelf(String name, BookshelfType type, String description, User user) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.user = user;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BookshelfType getType() {
        return type;
    }

    public void setType(BookshelfType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<BookshelfBook> getBookshelfBooks() {
        return Collections.unmodifiableSet(bookshelfBooks);
    }

    public void setBookshelfBooks(Set<BookshelfBook> bookshelfBooks) {
        this.bookshelfBooks = bookshelfBooks;
    }
}
