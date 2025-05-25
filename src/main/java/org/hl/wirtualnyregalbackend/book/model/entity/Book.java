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
    @JoinTable(name = "book_book_genre",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "book_genre_id"))
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
        this.bookSeriesBooks = bookSeriesBooks;
    }


    public void setIsbnIfNotNull(String isbn) {
        if (isbn != null) {
            this.isbn = isbn;
        }
    }

    public void setTitleIfNotNull(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    public void setPublicationYearIfNotNull(Integer publicationYear) {
        if (publicationYear != null) {
            this.publicationYear = publicationYear;
        }
    }

    public void setLanguageIfNotNull(Locale language) {
        if (language != null) {
            this.language = language;
        }
    }

    public void setPageCountIfNotNull(Integer pageCount) {
        if (pageCount != null) {
            this.pageCount = pageCount;
        }
    }

    public void setDescriptionIfNotNull(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public void setCoverIfNotNull(BookCover cover) {
        if (cover != null) {
            this.cover = cover;
        }
    }

    public void setFormatIfNotNull(BookFormat format) {
        if (format != null) {
            this.format = format;
        }
    }

    public void setPublisherIfNotNull(Publisher publisher) {
        if (publisher != null) {
            this.publisher = publisher;
        }
    }

    public void setGenresIfNotNull(Set<Genre> genres) {
        if (genres != null) {
            this.genres = genres;
        }
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

    public void setAuthorsIfNotNull(Set<Author> authors) {
        if (authors != null) {
            this.authors = authors;
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


    public void setBookSeriesBooksIfNotNull(List<BookSeriesBook> bookSeriesBooks) {
        if (bookSeriesBooks != null) {
            bookSeriesBooks.forEach(e -> e.setBook(this));
            this.bookSeriesBooks = bookSeriesBooks;
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


    public List<BookSeriesBook> getBookSeriesBooks() {
        return Collections.unmodifiableList(bookSeriesBooks);
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public Locale getLanguage() {
        return language;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public BookFormat getFormat() {
        return format;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

}
