package org.hl.wirtualnyregalbackend.book.model.entity;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.genre.model.Genre;
import org.hl.wirtualnyregalbackend.publisher.model.Publisher;

import java.util.*;

@Entity
@Table(name = "book")
public class Book extends BaseEntity {

    @Column(name = "order_in_series")
    private Integer orderInSeries;

    @OneToOne(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private BookCover cover;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookEdition> editions = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "book_genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(mappedBy = "books", fetch = FetchType.LAZY)
    private Set<BookSeries> series = new HashSet<>();

    protected Book() { }

    public Book(String isbn,
                String title,
                Set<Author> authors,
                Set<Genre> genres,
                Set<Publisher> publishers,
                Locale language,
                Integer numberOfPages,
                Integer publicationYear,
                BookFormat format) {
        BookEdition edition = new BookEdition(isbn, title, publicationYear, language, numberOfPages, publishers, format, this);
        editions.add(edition);
        this.authors = authors;
        this.genres = genres;
    }


    public void updateBookCover(String coverUrl) {
        cover.updateCoverUrl(coverUrl);
    }

    public void addGenre(Genre genre) {
        Objects.requireNonNull(genre, "genre cannot be null");
        if(genres.contains(genre)) {
            throw new InvalidRequestException("Genre is already assigned to this Book");
        }
        genres.add(genre);
    }

    public void removeGenre(Long bookGenreId) {
        Objects.requireNonNull(bookGenreId, "bookGenreId cannot be null");
        boolean isSuccess = this.genres.removeIf(genre -> genre.getId().equals(bookGenreId));
        if(!isSuccess) {
            throw new InvalidRequestException("Genre is not assigned to this book");
        }
    }

    public void updateAuthors(Set<Author> authors) {
        if(authors != null) {
            this.authors = authors;
        }
    }

    public void addAuthor(Author author) {
        Objects.requireNonNull(author, "author cannot be null");
        if(authors.contains(author)) {
            throw new InvalidRequestException("Author is already assigned to this book");
        }
        this.authors.add(author);
    }

    public void removeAuthor(Long authorId) {
        Objects.requireNonNull(authorId, "authorId cannot be null");
        boolean isSuccess = authors.removeIf(author -> author.getId().equals(authorId));
        if(!isSuccess) {
            throw new InvalidRequestException("Author is not assigned to this book");
        }
    }

    public BookCover getCover() {
        return cover;
    }

    public Set<Genre> getGenres() {
        return Collections.unmodifiableSet(genres);
    }

    public Set<Author> getAuthors() {
        return Collections.unmodifiableSet(authors);
    }

    public Integer getOrderInSeries() {
        return orderInSeries;
    }

    public List<BookEdition> getEditions() {
        return Collections.unmodifiableList(editions);
    }

    public Set<BookSeries> getSeries() {
        return Collections.unmodifiableSet(series);
    }

}
