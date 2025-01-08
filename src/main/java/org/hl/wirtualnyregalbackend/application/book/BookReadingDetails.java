package org.hl.wirtualnyregalbackend.application.book;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.infrastructure.jpa.UpdatableBaseEntity;
import org.hl.wirtualnyregalbackend.infrastructure.security.User;

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

    protected BookReadingDetails() { }

    public BookReadingDetails(Integer currentPage, User user, Book book) {
        this.user = Objects.requireNonNull(user, "user cannot be null.");
        this.book = Objects.requireNonNull(book, "book cannot be null.");
        this.currentPage = validateCurrentPage(currentPage);
        this.progressPercentage = calculateProgressPercentage(currentPage);
    }


    private Integer validateCurrentPage(Integer currentPage) {
        Objects.requireNonNull(currentPage, "currentPage cannot be null.");
        if(currentPage < 0) {
            throw new IllegalArgumentException("currentPage cannot be negative. Current page: %d".formatted(currentPage));
        }
        return currentPage;
    }

    private Integer calculateProgressPercentage(Integer currentPage) {
        int bookNumberOfPages = book.getNumOfPages();
        float progress = (float) currentPage / (float) bookNumberOfPages;
        return (int) progress * 100;
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }
}
