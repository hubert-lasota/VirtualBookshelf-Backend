package org.hl.wirtualnyregalbackend.book.model.entity;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.publisher.model.Publisher;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
public class BookEdition extends BaseEntity {

    @Column
    private String isbn;

    @Column
    private String title;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(name = "language_tag")
    private Locale language;

    @Column(name = "number_of_pages")
    private Integer numberOfPages;

    @Column
    private String description;

    @ManyToMany
    private Set<Publisher> publishers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "book_format_id")
    private BookFormat format;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    protected BookEdition() {
    }


    public BookEdition(String isbn,
                       String title,
                       Integer publicationYear,
                       Locale language,
                       Integer numberOfPages,
                       Set<Publisher> publishers,
                       BookFormat format,
                       Book book) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.language = language;
        this.numberOfPages = numberOfPages;
        this.publishers = publishers;
        this.format = format;
        this.book = book;
    }


    public void addPublisher(Publisher publisher) {
        if (publishers.contains(publisher)) {
            throw new InvalidRequestException("Publisher: %s is already in book publisher.".formatted(publisher.getName()));
        }
        this.publishers.add(publisher);
    }

    public void removePublisher(Long publisherId) {
        boolean isSuccess = publishers.removeIf(publisher -> publisher.getId().equals(publisherId));
        if (!isSuccess) {
            throw new InvalidRequestException("Book has no publisher with id %d.".formatted(publisherId));
        }
    }

    public void updateIsbn(String isbn) {
        if (isbn != null) {
            this.isbn = isbn;
        }
    }

    public void updateTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    public void updatePublicationYear(Integer publicationYear) {
        if (publicationYear != null) {
            this.publicationYear = publicationYear;
        }
    }

    public void updateLanguage(Locale language) {
        if (language != null) {
            this.language = language;
        }
    }

    public void updateNumberOfPages(Integer numberOfPages) {
        if (numberOfPages != null) {
            this.numberOfPages = numberOfPages;
        }
    }

    public void updateDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

    public void setFormat(BookFormat format) {
        this.format = format;
    }

    public void setPublishers(Set<Publisher> publishers) {
        this.publishers = publishers;
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

    public Integer getNumberOfPages() {
        return numberOfPages;
    }

    public Set<Publisher> getPublishers() {
        return Collections.unmodifiableSet(publishers);
    }

    public BookFormat getFormat() {
        return format;
    }

    public Book getBook() {
        return book;
    }

    public String getDescription() {
        return description;
    }

}
