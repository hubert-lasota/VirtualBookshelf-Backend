package org.hl.wirtualnyregalbackend.book.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.author.model.Author;
import org.hl.wirtualnyregalbackend.book.exception.InvalidBookIsbnException;
import org.hl.wirtualnyregalbackend.common.ActionResult;
import org.hl.wirtualnyregalbackend.common.ApiError;
import org.hl.wirtualnyregalbackend.common.jpa.BookIsbnConverter;
import org.hl.wirtualnyregalbackend.common.jpa.LocaleToStringConverter;
import org.hl.wirtualnyregalbackend.common.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.publisher.model.Publisher;
import org.hl.wirtualnyregalbackend.tag.Tag;

import java.time.Instant;
import java.util.*;

import static org.hl.wirtualnyregalbackend.common.ValidationUtils.baseValidateString;
import static org.hl.wirtualnyregalbackend.common.ValidationUtils.validateStringAndReturnResult;

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
    private BookCover cover;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "book_genre_id"))
    private Set<BookGenre> genres;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    @ManyToMany
    @JoinTable(name = "book_publisher",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id"))
    private Set<Publisher> publishers;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_tag",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    protected Book() { }

    public Book(String externalApiId,
                String isbn,
                String title,
                Set<Author> authors,
                Set<Publisher> publishers,
                String coverUrl,
                Instant publishedAt,
                Integer publishedYear,
                String description,
                Integer numOfPages,
                String languageTag) {
        this.externalApiId = externalApiId;
        this.title = baseValidateString(title, "title");
        this.authors = Objects.requireNonNull(authors, "authors cannot be null");
        this.publishers = Objects.requireNonNull(publishers, "publishers cannot be null");
        this.publishedAt = publishedAt;
        this.publishedYear = publishedYear;
        this.description = description;
        this.numOfPages = numOfPages;

        if(isbn != null) {
            this.isbn = new BookIsbn(isbn);
        }

        if(baseValidateString(coverUrl)) {
            this.cover = new BookCover(coverUrl, this);
        }

        if(baseValidateString(languageTag)) {
            this.language = Locale.forLanguageTag(languageTag);
        }
    }

    public Book(String externalApiId,
                String isbn,
                String title,
                String coverUrl,
                Instant publishedAt,
                Integer publishedYear,
                String description,
                Integer numOfPages,
                String languageTag) {
       this(externalApiId, isbn, title, Collections.emptySet(), Collections.emptySet(), coverUrl, publishedAt, publishedYear, description, numOfPages, languageTag);
    }


    public ActionResult updateIsbn(String isbn) {
        try {
            this.isbn = new BookIsbn(isbn);
            return new ActionResult(true, null);
        } catch (InvalidBookIsbnException e) {
            ApiError error = new ApiError("isbn", e.getMessage());
            return new ActionResult(false, error);
        }

    }

    public ActionResult updateTitle(String title) {
        ActionResult result = validateStringAndReturnResult(title, "title");
        if(result.success()) {
            this.title = title;
        }
        return result;
    }

    public ActionResult updateBookCover(String coverUrl) {
        return cover.updateCoverUrl(coverUrl);
    }

    public ActionResult updatePublishedAt(Instant publishedAt) {
        Objects.requireNonNull(publishedAt, "publishedAt cannot be null");
        this.publishedAt = publishedAt;
        return new ActionResult(true, null);
    }

    public ActionResult updatePublishedYear(Integer publishedYear) {
        Objects.requireNonNull(publishedYear, "publishedYear cannot be null");
        this.publishedYear = publishedYear;
        return new ActionResult(true, null);
    }

    public ActionResult updateDescription(String description) {
        ActionResult result = validateStringAndReturnResult(description, "description");
        if(result.success()) {
            this.description = description;
        }
        return result;
    }

    public ActionResult updateNumOfPages(Integer numOfPages) {
        this.numOfPages = numOfPages;
        return new ActionResult(true, null);
    }

    public ActionResult updateLanguage(String languageTag) {
        Locale locale = Locale.forLanguageTag(languageTag);
        if(locale.getLanguage().isEmpty()) {
            ApiError error = new ApiError("language", "This tag is invalid %s".formatted(languageTag));
            return new ActionResult(false, error);
        } else {
            this.language = locale;
            return new ActionResult(true, null);
        }
    }

    public ActionResult updateGenres(Set<BookGenre> genres) {
        this.genres = Objects.requireNonNull(genres, "genres cannot be null");
        return new ActionResult(true, null);
    }

    public ActionResult addGenre(BookGenre genre) {
        Objects.requireNonNull(genre, "genre cannot be null");
        if(genres.contains(genre)) {
            ApiError error = new ApiError("genres", "Book already contains genre %s".formatted(genre));
            return new ActionResult(false, error);
        }
        genres.add(genre);
        return new ActionResult(true, null);
    }

    public ActionResult removeGenre(Long bookGenreId) {
        Objects.requireNonNull(bookGenreId, "bookGenreId cannot be null");
        boolean isSuccess = this.genres.removeIf(genre -> genre.getId().equals(bookGenreId));
        return isSuccess ? new ActionResult(true, null) :
                new ActionResult(false, new ApiError("genres", "Book(id=%d) has no genre with id=%d".formatted(id, bookGenreId)));
    }

    public ActionResult updateAuthors(Set<Author> authors) {
        this.authors = Objects.requireNonNull(authors, "authors cannot be null");
        return new ActionResult(true, null);
    }

    public ActionResult addAuthor(Author author) {
        Objects.requireNonNull(author, "author cannot be null");
        if(authors.contains(author)) {
            ApiError error = new ApiError("author", author.toString());
            return new ActionResult(false, error);
        }
        this.authors.add(author);
        return new ActionResult(true, null);
    }

    public ActionResult removeAuthor(Long authorId) {
        Objects.requireNonNull(authorId, "authorId cannot be null");
        boolean isSuccess = authors.removeIf(author -> author.getId().equals(authorId));
        return isSuccess ? new ActionResult(true, null) :
                new ActionResult(false, new ApiError("authors","Book(id=%d) has no author with id %d.".formatted(id, authorId)));
    }

    public ActionResult updatePublishers(Set<Publisher> publishers) {
        this.publishers = Objects.requireNonNull(publishers, "publishers cannot be null");
        return new ActionResult(true, null);
    }

    public ActionResult addPublisher(Publisher publisher) {
        Objects.requireNonNull(publisher, "publisher cannot be null");
        if(publishers.contains(publisher)) {
            ApiError error = new ApiError("publisher", "Publisher: %s is already in book publisher.".formatted(publisher.getName()));
            return new ActionResult(false, error);
        }
        this.publishers.add(publisher);
        return new ActionResult(true, null);
    }

    public ActionResult removePublisher(Long publisherId) {
        Objects.requireNonNull(publisherId, "publisherId cannot be null");
        boolean isSuccess = publishers.removeIf(publisher -> publisher.getId().equals(publisherId));
        return isSuccess ? new ActionResult(true, null) :
                new ActionResult(false, new ApiError("publishers", "Book(id=%d) has no publisher with id %d."));
    }

    public ActionResult updateTags(Set<Tag> tags) {
        this.tags = Objects.requireNonNull(tags, "tags cannot be null");
        return new ActionResult(true, null);
    }

    public ActionResult addTag(Tag tag) {
        Objects.requireNonNull(tag, "tag cannot be null");
        if(tags.contains(tag)) {
            ApiError error = new ApiError("tag", tag.toString());
            return new ActionResult(false, error);
        }
        this.tags.add(tag);
        return new ActionResult(true, null);
    }

    public ActionResult removeTag(Long tagId) {
        Objects.requireNonNull(tagId, "tagId cannot be null");
        boolean isSuccess = tags.removeIf(tag -> tag.getId().equals(tagId));
        return isSuccess ? new ActionResult(true, null) :
                new ActionResult(false, new ApiError("tags","Book(id=%d) has no tag with id %d."));
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

    public BookCover getCover() {
        return cover;
    }

    public Set<BookGenre> getGenres() {
        return Collections.unmodifiableSet(genres);
    }

    public Set<Author> getAuthors() {
        return Collections.unmodifiableSet(authors);
    }

    public Locale getLanguage() {
        return language;
    }

    public Set<Publisher> getPublishers() {
        return Collections.unmodifiableSet(publishers);
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
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
