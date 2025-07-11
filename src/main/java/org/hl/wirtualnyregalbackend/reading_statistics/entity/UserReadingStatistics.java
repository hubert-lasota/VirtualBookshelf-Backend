package org.hl.wirtualnyregalbackend.reading_statistics.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.security.entity.User;

@Entity
@Table(name = "user_reading_statistics")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UserReadingStatistics extends BaseEntity {

    @Column(name = "total_read_books")
    private Long totalReadBooks;

    @Column(name = "total_read_pages")
    private Long totalReadPages;

    @Column(name = "most_pages_read_in_session")
    private Integer mostPagesReadInSession;

    @Column(name = "total_read_minutes")
    private Long totalReadMinutes;

    @Column(name = "longest_read_minutes")
    private Integer longestReadMinutes;

    @Column(name = "current_reading_streak")
    private Integer currentReadingStreak;

    @Column(name = "longest_reading_streak")
    private Integer longestReadingStreak;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserReadingStatistics(User user) {
        this.user = user;
        this.totalReadBooks = 0L;
        this.totalReadPages = 0L;
        this.totalReadMinutes = 0L;
        this.longestReadMinutes = 0;
        this.mostPagesReadInSession = 0;
        this.currentReadingStreak = 0;
        this.longestReadingStreak = 0;
    }

    public void incrementTotalReadBooks() {
        this.totalReadBooks++;
    }

    public void incrementCurrentReadingStreak() {
        this.currentReadingStreak++;
        if (currentReadingStreak > longestReadingStreak) {
            this.longestReadingStreak = currentReadingStreak;
        }
    }

    public void resetCurrentReadingStreak() {
        this.currentReadingStreak = 0;
    }

    public void addReadPages(Integer readPages) {
        this.totalReadPages += readPages;
        if (readPages > this.mostPagesReadInSession) {
            this.mostPagesReadInSession = readPages;
        }
    }

    public void addReadMinutes(Integer readMinutes) {
        this.totalReadMinutes += readMinutes;
        if (readMinutes > this.longestReadMinutes) {
            this.longestReadMinutes = readMinutes;
        }
    }

}
