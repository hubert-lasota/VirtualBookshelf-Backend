package org.hl.wirtualnyregalbackend.book.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book_cover.entity.BookCover;
import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;

import java.util.*;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseEntity {

    @Column
    private String isbn;

    @Column
    private String title;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(name = "language_tag")
    private Locale language;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column
    private String description;

    @OneToOne(mappedBy = "book", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private BookCover cover;

    @ManyToOne
    @JoinColumn(name = "book_format_id")
    private BookFormat format;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(mappedBy = "book")
    private List<BookSeriesBook> bookSeriesBooks = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_genre",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "book_author",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();


    public Book(String isbn,
                String title,
                Integer publicationYear,
                Locale language,
                Integer pageCount,
                BookCover cover,
                BookFormat format,
                Publisher publisher,
                Set<Author> authors,
                Set<Genre> genres,
                List<BookSeriesBook> bookSeriesBooks) {
        this.isbn = isbn;
        this.title = title;
        this.publicationYear = publicationYear;
        this.language = language;
        this.pageCount = pageCount;
        this.cover = cover;
        this.format = format;
        this.publisher = publisher;
        this.authors = authors;
        this.genres = genres;
        setBookSeriesBooks(bookSeriesBooks);
    }

    public void addGenre(Genre genre) {
        Objects.requireNonNull(genre, "genre cannot be null");
        if (genres.contains(genre)) {
            throw new InvalidRequestException("Genre is already assigned to this Book");
        }
        genres.add(genre);
    }

    public void removeGenre(Long bookGenreId) {
        Objects.requireNonNull(bookGenreId, "bookGenreId cannot be null");
        boolean isSuccess = this.genres.removeIf(genre -> genre.getId().equals(bookGenreId));
        if (!isSuccess) {
            throw new InvalidRequestException("Genre is not assigned to this book");
        }
    }

    public void addAuthor(Author author) {
        Objects.requireNonNull(author, "author cannot be null");
        if (authors.contains(author)) {
            throw new InvalidRequestException("Author is already assigned to this book");
        }
        this.authors.add(author);
    }

    public void removeAuthor(Long authorId) {
        Objects.requireNonNull(authorId, "authorId cannot be null");
        boolean isSuccess = authors.removeIf(author -> author.getId().equals(authorId));
        if (!isSuccess) {
            throw new InvalidRequestException("Author is not assigned to this book");
        }
    }


    public Set<Genre> getGenres() {
        return Collections.unmodifiableSet(genres);
    }

    public Set<Author> getAuthors() {
        return Collections.unmodifiableSet(authors);
    }

    public List<BookSeriesBook> getBookSeriesBooks() {
        return Collections.unmodifiableList(bookSeriesBooks);
    }

    public void setBookSeriesBooks(List<BookSeriesBook> bookSeriesBooks) {
        bookSeriesBooks.forEach(e -> e.setBook(this));
        this.bookSeriesBooks = bookSeriesBooks;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        if (this.id != null && book.id != null) {
            return this.id.equals(book.id);
        }
        return Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && Objects.equals(publicationYear, book.publicationYear) && Objects.equals(language, book.language) && Objects.equals(pageCount, book.pageCount) && Objects.equals(description, book.description) && Objects.equals(cover, book.cover) && Objects.equals(format, book.format) && Objects.equals(publisher, book.publisher) && Objects.equals(bookSeriesBooks, book.bookSeriesBooks) && Objects.equals(genres, book.genres) && Objects.equals(authors, book.authors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, publicationYear, language, pageCount, description, cover, format, publisher, bookSeriesBooks, genres, authors);
    }

}
