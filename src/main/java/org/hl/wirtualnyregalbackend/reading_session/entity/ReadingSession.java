package org.hl.wirtualnyregalbackend.reading_session.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;

import java.time.Instant;

@Entity
@Table(name = "reading_session")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingSession extends BaseEntity {

    @Column(name = "page_from")
    private Integer pageFrom;

    @Column(name = "page_to")
    private Integer pageTo;

    @Column(name = "started_reading_at")
    private Instant startedReadingAt;

    @Column(name = "finished_reading_at")
    private Instant finishedReadingAt;

    @ManyToOne
    @JoinColumn(name = "reading_book_id")
    private ReadingBook readingBook;


    public ReadingSession(Integer pageFrom,
                          Integer pageTo,
                          Instant startedReadingAt,
                          Instant finishedReadingAt,
                          ReadingBook readingBook) {
        this.readingBook = readingBook;
        setPageRange(pageFrom, pageTo);
        setReadingPeriod(startedReadingAt, finishedReadingAt);
    }

    public void setPageRange(Integer pageFrom, Integer pageTo) {
        if (pageFrom > pageTo) {
            throw new InvalidRequestException("Page from cannot be greater than page to");
        }
        this.pageFrom = pageFrom;
        this.pageTo = pageTo;
    }

    public void setReadingPeriod(Instant startedReadingAt, Instant finishedReadingAt) {
        if (finishedReadingAt != null && startedReadingAt.isAfter(finishedReadingAt)) {
            throw new InvalidRequestException("Started reading time must be before finished reading time.");
        }
        this.startedReadingAt = startedReadingAt;
        this.finishedReadingAt = finishedReadingAt;
    }

}
