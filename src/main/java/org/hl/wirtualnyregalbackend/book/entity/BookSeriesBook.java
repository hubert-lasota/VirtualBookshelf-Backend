package org.hl.wirtualnyregalbackend.book.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.book_series.entity.BookSeries;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "book_series_book")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookSeriesBook extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "book_series_id")
    private BookSeries bookSeries;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "book_order")
    private Integer bookOrder;


    public BookSeriesBook(BookSeries bookSeries, Integer bookOrder) {
        this.bookSeries = bookSeries;
        this.bookOrder = bookOrder;
    }


}
