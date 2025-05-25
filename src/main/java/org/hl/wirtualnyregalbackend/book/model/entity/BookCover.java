package org.hl.wirtualnyregalbackend.book.model.entity;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "book_cover")
public class BookCover extends BaseEntity {

    @Column
    private String url;

    @OneToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToOne
    @JoinColumn(name = "book_cover_binary_id")
    private BookCoverBinary coverImg;

    protected BookCover() {
    }

    public BookCover(String url, BookCoverBinary coverImg) {
        this.url = url;
        this.coverImg = coverImg;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setUrlIfNotNull(String url) {
        if (url != null) {
            this.url = url;
        }
    }

    public String getUrl() {
        return url;
    }

}
