package org.hl.wirtualnyregalbackend.reading_book.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.validation.RangeDateValidator;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;

import java.time.Instant;

@Entity
@Table(name = "reading_book")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingBook extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    @Setter
    private ReadingStatus status;

    @Column
    private Instant startedReadingAt;

    @Column
    private Instant finishedReadingAt;

    @ManyToOne
    @JoinColumn(name = "bookshelf_id")
    @Setter
    private Bookshelf bookshelf;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    public ReadingBook(ReadingStatus status,
                       Instant startedReadingAt,
                       Instant finishedReadingAt,
                       Bookshelf bookshelf,
                       Book book) {
        this.status = status;
        this.bookshelf = bookshelf;
        this.book = book;
        setReadingPeriod(startedReadingAt, finishedReadingAt);
    }

    public void setReadingPeriod(Instant startedReadingAt, Instant finishedReadingAt) {
        RangeDateValidator.validate(startedReadingAt, finishedReadingAt, "startedReadingAt", "finishedReadingAt");
        this.startedReadingAt = startedReadingAt;
        this.finishedReadingAt = finishedReadingAt;
    }

}
