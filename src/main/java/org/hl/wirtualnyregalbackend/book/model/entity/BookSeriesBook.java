package org.hl.wirtualnyregalbackend.book.model.entity;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.book_series.model.BookSeries;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "book_series_book")
public class BookSeriesBook extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "book_series_id")
    private BookSeries bookSeries;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "book_order")
    private Integer bookOrder;

    protected BookSeriesBook() {
    }

    public BookSeriesBook(BookSeries bookSeries, Integer bookOrder) {
        this.bookSeries = bookSeries;
        this.bookOrder = bookOrder;
    }

    public BookSeries getBookSeries() {
        return bookSeries;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getBookOrder() {
        return bookOrder;
    }

}
