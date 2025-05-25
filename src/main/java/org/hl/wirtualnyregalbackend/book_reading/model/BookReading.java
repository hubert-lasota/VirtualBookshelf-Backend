package org.hl.wirtualnyregalbackend.book_reading.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.book.model.entity.Book;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.jpa.RangeDate;
import org.hl.wirtualnyregalbackend.security.model.User;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "book_reading")
public class BookReading extends BaseEntity {

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

    @Column
    @Enumerated(EnumType.STRING)
    private BookReadingStatus status;

    @Embedded
    private RangeDate rangeDate;

    protected BookReading() {
    }

    public BookReading(Integer currentPage,
                       User user,
                       Book book,
                       RangeDate rangeDate,
                       BookReadingStatus status) {
        this.user = Objects.requireNonNull(user, "user cannot be null.");
        this.book = Objects.requireNonNull(book, "book cannot be null.");
        this.rangeDate = rangeDate;
        this.currentPage = validateCurrentPage(currentPage);
        this.progressPercentage = calculateProgressPercentage(currentPage);
        this.status = status;
    }

    public void updateCurrentPage(Integer currentPage) {
        this.currentPage = validateCurrentPage(currentPage);
        this.progressPercentage = calculateProgressPercentage(currentPage);
    }

    public void setRangeDateIfNotNull(RangeDate rangeDate) {
        if (rangeDate != null) {
            this.rangeDate = rangeDate;
        }
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Instant getStartedAt() {
        return rangeDate.getStartedAt();
    }

    public Instant getFinishedAt() {
        return rangeDate.getEndedAt();
    }

    public BookReadingStatus getStatus() {
        return status;
    }

    private Integer validateCurrentPage(Integer currentPage) {
        int bookPageCount = book.getPageCount();
        if (currentPage > bookPageCount) {
            throw new InvalidRequestException("Current page is higher than book number of pages");
        }
        return currentPage;
    }

    private Integer calculateProgressPercentage(Integer currentPage) {
        int bookPageCount = book.getPageCount();
        float progress = (float) currentPage / (float) bookPageCount;
        return (int) progress * 100;
    }

}
