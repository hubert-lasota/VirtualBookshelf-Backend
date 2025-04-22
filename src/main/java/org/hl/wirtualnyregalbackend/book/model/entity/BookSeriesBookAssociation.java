package org.hl.wirtualnyregalbackend.book.model.entity;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "book_book_series")
public class BookSeriesBookAssociation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "book_series_id")
    private BookSeries bookSeries;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column
    private Integer bookOrder;


    public BookSeriesBookAssociation(BookSeries bookSeries, Book book, Integer bookOrder) {
        this.bookSeries = bookSeries;
        this.book = book;
        this.bookOrder = bookOrder;
    }

    public BookSeries getBookSeries() {
        return bookSeries;
    }

    public Book getBook() {
        return book;
    }

    public Integer getBookOrder() {
        return bookOrder;
    }

}
