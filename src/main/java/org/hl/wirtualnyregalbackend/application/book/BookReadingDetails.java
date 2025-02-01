package org.hl.wirtualnyregalbackend.application.book;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.application.common.ActionType;
import org.hl.wirtualnyregalbackend.application.common.ApiError;
import org.hl.wirtualnyregalbackend.application.common.InvalidRequestException;
import org.hl.wirtualnyregalbackend.application.common.RangeDate;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "book_reading_details")
public class BookReadingDetails extends UpdatableBaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "current_page")
    private Integer currentPage;

    @Column(name = "progress_percentage")
    private Integer progressPercentage;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startAt", column = @Column(name = "started_at_timestamp")),
            @AttributeOverride(name = "endAt", column = @Column(name = "finished_at_timestamp"))
    })
    private RangeDate rangeDate;

    protected BookReadingDetails() { }

    public BookReadingDetails(Integer currentPage, User user, Book book, Instant startedAt, Instant finishedAt) {
        this.user = Objects.requireNonNull(user, "user cannot be null.");
        this.book = Objects.requireNonNull(book, "book cannot be null.");
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
        Objects.requireNonNull(currentPage, "currentPage cannot be null.");
        if(currentPage < 0) {
            throw new IllegalArgumentException("currentPage cannot be negative. Current page: %d".formatted(currentPage));
        }
        int bookPages = book.getNumOfPages();
        if(currentPage > bookPages) {
            ApiError error = new ApiError("currentPage", "Current page is greater than the book's number of pages");
            throw new InvalidRequestException(List.of(error), ActionType.UPDATE, "Current page is higher than book number of pages");
        }
        return currentPage;
    }

    private Integer calculateProgressPercentage(Integer currentPage) {
        int bookNumberOfPages = book.getNumOfPages();
        float progress = (float) currentPage / (float) bookNumberOfPages;
        return (int) progress * 100;
    }

}
