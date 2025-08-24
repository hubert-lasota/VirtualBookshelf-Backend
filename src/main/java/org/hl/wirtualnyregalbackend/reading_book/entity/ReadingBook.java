package org.hl.wirtualnyregalbackend.reading_book.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "reading_book")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReadingBook extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private ReadingStatus status;

    @Embedded
    private ReadingBookDurationRange durationRange;

    @ManyToOne
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @Setter(AccessLevel.NONE)
    private Book book;

    @Column(name = "current_page")
    private Integer currentPage;

    @Column(name = "total_notes")
    @Setter(AccessLevel.NONE)
    private Long totalNotes;

    @Column(name = "total_sessions")
    @Setter(AccessLevel.NONE)
    private Long totalSessions;


    public ReadingBook(ReadingStatus status, ReadingBookDurationRange durationRange, Bookshelf bookshelf, Book book) {
        this.status = status;
        this.durationRange = durationRange;
        this.bookshelf = bookshelf;
        this.book = book;
        this.currentPage = 0;
        this.totalNotes = 0L;
        this.totalSessions = 0L;
    }


    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
        if (this.currentPage.equals(book.getPageCount())) {
            this.status = ReadingStatus.READ;
        }

    }

    public Float calculateProgressPercentage() {
        if (currentPage.equals(0)) {
            return 0F;
        }
        float value = ((float) currentPage) / book.getPageCount() * 100F;
        return BigDecimal.valueOf(value)
            .setScale(2, RoundingMode.HALF_UP)
            .floatValue();
    }

    public void incrementTotalNotes() {
        this.totalNotes++;
    }

    public void decrementTotalNotes() {
        this.totalNotes--;
    }

    public void incrementTotalSessions() {
        this.totalSessions++;
    }

    public void decrementTotalSessions() {
        this.totalSessions--;
    }

}
