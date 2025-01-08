package org.hl.wirtualnyregalbackend.application.book;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.application.author.Author;
import org.hl.wirtualnyregalbackend.application.book.exception.IllegalBookOperationException;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.BookIsbnConverter;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.LocaleToStringConverter;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;

import java.time.Instant;
import java.util.*;

import static org.hl.wirtualnyregalbackend.application.common.ValidationUtils.baseValidateString;

@Entity
@Table(name = "book")
public class Book extends UpdatableBaseEntity {

    @Column(name = "external_api_id")
    private String externalApiId;

    @Column(name = "isbn")
    @Convert(converter = BookIsbnConverter.class)
    private BookIsbn isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "published_at_timestamp")
    private Instant publishedAt;

    @Column(name = "published_year")
    private Integer publishedYear;

    @Column(name = "description")
    private String description;

    @Column(name = "language")
    @Convert(converter = LocaleToStringConverter.class)
    private Locale language;

    @Column(name = "num_of_pages")
    private Integer numOfPages;


    @OneToOne(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private BookCover bookCover;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookRating> bookRatings;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "book_genre_id"))
    private Set<BookGenre> bookGenres;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    protected Book() { }

    public Book(String externalApiId,
                String isbn,
                String title,
                String coverUrl,
                Instant publishedAt,
                Integer publishedYear,
                String description,
                Integer numOfPages,
                String languageTag) {
        this.externalApiId = externalApiId;
        this.isbn = new BookIsbn(isbn);
        this.title = baseValidateString(title, "title");
        this.publishedAt = publishedAt;
        this.publishedYear = publishedYear;
        this.description = description;
        this.numOfPages = numOfPages;

        if(baseValidateString(coverUrl)) {
            this.bookCover = new BookCover(coverUrl, this);
        }

        if(baseValidateString(languageTag)) {
            this.language = Locale.forLanguageTag(languageTag);
        }
    }

    public void updateBookCover(String coverUrl) {
        bookCover.updateCoverUrl(coverUrl);
    }

    public void addAuthor(Author author) {
        Objects.requireNonNull(author);
        if(authors.contains(author)) {
            throw new IllegalBookOperationException(id, "add", "Author with id %d is already in book author list."
                    .formatted(author.getId()));
        }
        this.authors.add(author);
    }

    public void removeAuthor(Long authorId) {
        Objects.requireNonNull(authorId);
        boolean isSuccess = authors.removeIf(author -> author.getId().equals(authorId));
        if(!isSuccess) {
            throw new IllegalBookOperationException(id, "remove", "Author with id %d is not in book author list.");
        }
    }

    public String getExternalApiId() {
        return externalApiId;
    }

    public BookIsbn getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public String getDescription() {
        return description;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public BookCover getBookCover() {
        return bookCover;
    }

    public Set<BookGenre> getBookGenres() {
        return Collections.unmodifiableSet(bookGenres);
    }

    public Set<Author> getAuthors() {
        return Collections.unmodifiableSet(authors);
    }

    public Locale getLanguage() {
        return language;
    }

    public List<BookRating> getBookRatings() {
        return Collections.unmodifiableList(bookRatings);
    }

    @Override
    public String toString() {
        return "Book{" +
                ", id=" + id +
                ", externalApiId='" + externalApiId + '\'' +
                "title='" + title + '\'' +
                ", isbn=" + isbn + '}';
    }
}
