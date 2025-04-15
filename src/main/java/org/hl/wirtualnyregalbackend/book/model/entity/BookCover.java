package org.hl.wirtualnyregalbackend.book.model.entity;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "book_cover")
public class BookCover extends BaseEntity {

    @Column(name = "cover_url")
    private String coverUrl;

    @OneToOne(optional = false)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToOne
    @JoinColumn(name = "book_cover_img_id")
    private BookCoverImg coverImg;

    protected BookCover() { }

    public BookCover(String coverUrl, Book book, BookCoverImg coverImg) {
        this.coverUrl = coverUrl;
        this.book = book;
        this.coverImg = coverImg;
    }

    public void updateCoverUrl(String coverUrl) {
        if(coverUrl != null) this.coverUrl = coverUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

}
