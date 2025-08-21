package org.hl.wirtualnyregalbackend.book.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.book_cover.entity.BookCover;
import org.hl.wirtualnyregalbackend.book_format.entity.BookFormat;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.publisher.entity.Publisher;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Book extends BaseEntity {

    @Column
    private String isbn;

    @Column
    private String title;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Column(name = "language_code")
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

    public Set<Genre> getGenres() {
        return Collections.unmodifiableSet(genres);
    }

    public Set<Author> getAuthors() {
        return Collections.unmodifiableSet(authors);
    }

}
