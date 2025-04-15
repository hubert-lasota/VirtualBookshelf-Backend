package org.hl.wirtualnyregalbackend.book_reading.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.book.model.entity.BookEdition;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.jpa.RangeDate;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "book_reading_details")
public class BookReadingDetails extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_edition_id")
    private BookEdition bookEdition;

    @Column(name = "current_page")
    private Integer currentPage;

    @Column(name = "progress_percentage")
    private Integer progressPercentage;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startAt", column = @Column(name = "started_at")),
            @AttributeOverride(name = "endAt", column = @Column(name = "finished_at"))
    })
    private RangeDate rangeDate;

    protected BookReadingDetails() { }

    public BookReadingDetails(Integer currentPage,
                              User user,
                              BookEdition bookEdition,
                              Instant startedAt,
                              Instant finishedAt) {
        this.user = Objects.requireNonNull(user, "user cannot be null.");
        this.bookEdition = Objects.requireNonNull(bookEdition, "book cannot be null.");
        this.rangeDate = new RangeDate(startedAt, finishedAt);
        this.currentPage = validateCurrentPage(currentPage);
        this.progressPercentage = calculateProgressPercentage(currentPage);
    }

    public void updateCurrentPage(Integer currentPage) {
        this.currentPage = validateCurrentPage(currentPage);
        this.progressPercentage = calculateProgressPercentage(currentPage);
    }

    public void updateStartedAt(Instant startedAt) {
        Instant finishedAt = rangeDate.getEndAt();
        this.rangeDate = new RangeDate(startedAt, finishedAt);
    }

    public void updateFinishedAt(Instant finishedAt) {
        Instant startedAt = rangeDate.getStartAt();
        this.rangeDate = new RangeDate(startedAt, finishedAt);
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Instant getStartedAt() {
        return rangeDate.getStartAt();
    }

    public Instant getFinishedAt() {
        return rangeDate.getEndAt();
    }


    private Integer validateCurrentPage(Integer currentPage) {
        int bookPages = bookEdition.getNumberOfPages();
        if(currentPage > bookPages) {
            throw new InvalidRequestException("Current page is higher than book number of pages");
        }
        return currentPage;
    }

    private Integer calculateProgressPercentage(Integer currentPage) {
        int bookNumberOfPages = bookEdition.getNumberOfPages();
        float progress = (float) currentPage / (float) bookNumberOfPages;
        return (int) progress * 100;
    }

}
