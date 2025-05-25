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
    @JoinTable(name = "bookshelf_book",
        joinColumns = @JoinColumn(name = "bookshelf_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books = new HashSet<>();

    protected Bookshelf() {
    }

    public Bookshelf(String name, BookshelfType type, String description, User user) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.user = user;
    }

    public void addBook(Book book) {
        if (books.contains(book)) {
            throw new InvalidRequestException("Book is already in bookshelf");
        }
        books.add(book);
    }

    public void removeBookEdition(Book book) {
        if (!books.contains(book)) {
            throw new InvalidRequestException("Book edition is not in bookshelf");
        }
        books.remove(book);
    }

    public void setNameIfNotNull(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    public void setTypeIfNotNull(BookshelfType type) {
        if (type != null) {
            this.type = type;
        }
    }

    public void setDescriptionIfNotNull(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public void setBooksIfNotNull(Set<Book> books) {
        if (books != null) {
            this.books = books;
        }
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
