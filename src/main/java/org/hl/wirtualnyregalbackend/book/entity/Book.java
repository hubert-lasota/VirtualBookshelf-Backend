package org.hl.wirtualnyregalbackend.book.entity;

import jakarta.persistence.*;
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


    protected Book() {
    }

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
        bookSeriesBooks.forEach(e -> e.setBook(this));
        this.bookSeriesBooks = bookSeriesBooks;
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

    public BookCover getCover() {
        return cover;
    }

    public void setCover(BookCover cover) {
        this.cover = cover;
    }

    public Set<Genre> getGenres() {
        return Collections.unmodifiableSet(genres);
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Author> getAuthors() {
        return Collections.unmodifiableSet(authors);
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public List<BookSeriesBook> getBookSeriesBooks() {
        return Collections.unmodifiableList(bookSeriesBooks);
    }

    public void setBookSeriesBooks(List<BookSeriesBook> bookSeriesBooks) {
        bookSeriesBooks.forEach(e -> e.setBook(this));
        this.bookSeriesBooks = bookSeriesBooks;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Locale getLanguage() {
        return language;
    }

    public void setLanguage(Locale language) {
        this.language = language;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public BookFormat getFormat() {
        return format;
    }

    public void setFormat(BookFormat format) {
        this.format = format;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
