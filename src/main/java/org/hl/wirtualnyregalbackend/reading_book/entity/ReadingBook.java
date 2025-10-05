package org.hl.wirtualnyregalbackend.reading_book.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.reading_book.exception.InvalidReadingBookDurationRangeException;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Entity
@Table(name = "reading_book")
@Getter
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
    @Setter
    private Bookshelf bookshelf;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "current_page")
    private int currentPage;

    @Column(name = "total_notes")
    private long totalNotes;

    @Column(name = "total_sessions")
    private long totalSessions;


    public ReadingBook(ReadingStatus status, ReadingBookDurationRange durationRange, Bookshelf bookshelf, Book book) {
        changeStatus(status, durationRange);
        this.bookshelf = bookshelf;
        this.book = book;
        this.currentPage = 0;
        this.totalNotes = 0L;
        this.totalSessions = 0L;
    }

    public void changeCurrentPage(Integer currentPage) {
        if (currentPage < 0) {
            this.currentPage = 0;
        } else if (currentPage > book.getPageCount()) {
            this.currentPage = book.getPageCount();
        } else {
            this.currentPage = currentPage;
        }
    }


    public void changeStatus(ReadingStatus status, @Nullable ReadingBookDurationRange durationRange) {
        switch (status) {
            case WANT_TO_READ -> {
                this.status = ReadingStatus.WANT_TO_READ;
                this.durationRange = ReadingBookDurationRange.of(null, null);
            }
            case READ -> {
                Objects.requireNonNull(durationRange, "DurationRange must be provided for ReadingStatus='%s'".formatted(ReadingStatus.READ.toString()));
                if (durationRange.getFinishedAt() == null) {
                    throw new InvalidReadingBookDurationRangeException(durationRange, "FinishedAt must be provided for ReadingStatus='%s'".formatted(ReadingStatus.READ.toString()));
                }
                this.status = ReadingStatus.READ;
                this.durationRange = durationRange;
                this.currentPage = this.book.getPageCount();
            }
            case READING -> {
                Objects.requireNonNull(durationRange, "DurationRange must be provided for ReadingStatus='%s'".formatted(ReadingStatus.READING.toString()));
                if (durationRange.getFinishedAt() == null) {
                    throw new InvalidReadingBookDurationRangeException(durationRange, "StartedAt must be provided for ReadingStatus='%s'".formatted(ReadingStatus.READING.toString()));
                }
                this.status = ReadingStatus.READING;
                this.durationRange = ReadingBookDurationRange.of(durationRange.getStartedAt(), null);
            }
        }
    }

    public void addReadPages(Integer readPages, @Nullable ReadingBookDurationRange durationRange) {
        int currentPage = this.currentPage + readPages;
        ReadingStatus status;
        if (currentPage > book.getPageCount()) {
            this.currentPage = book.getPageCount();
            status = ReadingStatus.READ;
        } else {
            this.currentPage = Math.max(currentPage, 0);
            status = ReadingStatus.READING;
        }
        changeStatus(status, durationRange);
    }

    public Float calculateProgressPercentage() {
        if (currentPage == 0) {
            return 0F;
        }
        float value = ((float) currentPage) / book.getPageCount() * 100F;
        return BigDecimal
            .valueOf(value)
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
