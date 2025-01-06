package org.hl.wirtualnyregalbackend.application.bookshelf;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.application.book.Book;
import org.hl.wirtualnyregalbackend.application.bookshelf.exception.IllegalBookshelfOperationException;
import org.hl.wirtualnyregalbackend.application.bookshelf.exception.InvalidBookshelfTypeException;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;

import java.util.Objects;
import java.util.Optional;
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
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.description = description;
        this.user = Objects.requireNonNull(user, "User cannot be null");
    }

    public void addBook(Book book) {
        Objects.requireNonNull(book, "Book cannot be null");
        if(books.contains(book)) {
            throw new IllegalBookshelfOperationException(id, "add",
                    "Book with id = %d is already in the bookshelf.".formatted(book.getId()));
        }
        books.add(book);
    }

    public void removeBook(Long bookId) {
        Objects.requireNonNull(bookId, "BookId cannot be null");
        Optional<Book> bookOpt = books.stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst();
        if(bookOpt.isEmpty()) {
            throw new IllegalBookshelfOperationException(id, "remove",
                    "Book with id = %d is not in the bookshelf.".formatted(bookId));
        }
        books.remove(bookOpt.get());
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
        return books;
    }

}
