package org.hl.wirtualnyregalbackend.book_cover.entity;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.book.entity.Book;
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
    private BookCoverBinary coverBinary;

    protected BookCover() {
    }

    public BookCover(String url, BookCoverBinary coverBinary) {
        this.url = url;
        this.coverBinary = coverBinary;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BookCoverBinary getCoverBinary() {
        return coverBinary;
    }

}
