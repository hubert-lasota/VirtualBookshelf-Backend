package org.hl.wirtualnyregalbackend.application.book;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;

import java.util.Objects;

@Entity
@Table(name = "book_cover")
public class BookCover extends UpdatableBaseEntity {

    @Column(name = "cover_url")
    private String coverUrl;

    @OneToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    protected BookCover() { }

    public BookCover(String coverUrl, Book book) {
        Objects.requireNonNull(coverUrl, "Cover url cannot be null");
        Objects.requireNonNull(book, "Book cannot be null");
        this.coverUrl = coverUrl;
        this.book = book;
    }

    public void updateCoverUrl(String coverUrl) {
        Objects.requireNonNull(coverUrl, "Cover url cannot be null");
        this.coverUrl = coverUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

}
