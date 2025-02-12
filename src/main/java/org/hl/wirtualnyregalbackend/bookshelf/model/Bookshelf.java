package org.hl.wirtualnyregalbackend.bookshelf.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.book.model.Book;
import org.hl.wirtualnyregalbackend.bookshelf.exception.InvalidBookshelfTypeException;
import org.hl.wirtualnyregalbackend.common.ActionResult;
import org.hl.wirtualnyregalbackend.common.ApiError;
import org.hl.wirtualnyregalbackend.common.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "bookshelf")
public class Bookshelf extends UpdatableBaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookshelfType type;

    @Column(name = "description")
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "bookshelf_book",
            joinColumns = @JoinColumn(name = "bookshelf_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books;

    protected Bookshelf() { }

    public Bookshelf(String name, String type, String description, User user) {
        try {
            this.type = BookshelfType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidBookshelfTypeException(type);
        }
        this.name = Objects.requireNonNull(name, "name cannot be null");
        this.description = description;
        this.user = Objects.requireNonNull(user, "user cannot be null");
    }

    public ActionResult addBook(Book book) {
        Objects.requireNonNull(book, "book cannot be null");
        if(books.contains(book)) {
            ApiError error = new ApiError("books", "Book is already in bookshelf");
            return new ActionResult(false, error);
        }
        books.add(book);
        return new ActionResult(true, null);
    }

    public ActionResult removeBook(Long bookId) {
        Objects.requireNonNull(bookId, "bookId cannot be null");
        boolean isSuccess = books.removeIf(book -> book.getId().equals(bookId));
        return isSuccess ? new ActionResult(true, null) :
                new ActionResult(false, new ApiError("books", "Book is not in bookshelf"));
    }

    public String getName() {
        return name;
    }

    public BookshelfType getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public Set<Book> getBooks() {
        return Collections.unmodifiableSet(books);
    }

}
