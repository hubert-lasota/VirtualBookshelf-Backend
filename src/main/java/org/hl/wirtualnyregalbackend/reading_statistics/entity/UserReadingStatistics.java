package org.hl.wirtualnyregalbackend.reading_statistics.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

import java.time.YearMonth;

@Entity
@Table(name = "user_reading_statistics")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class UserReadingStatistics extends BaseEntity {

    @Column(name = "total_read_books")
    private Long readBookCount;

    @Column(name = "total_read_pages")
    private Long readPageCount;

    @Column(name = "most_pages_read_in_session")
    private Integer mostPagesReadInSession;

    @Column(name = "total_read_minutes")
    private Long readMinuteCount;

    @Column(name = "longest_read_minutes")
    private Integer longestReadMinutes;

    @Column(name = "current_reading_streak")
    private Integer currentReadingStreak;

    @Column(name = "longest_reading_streak")
    private Integer longestReadingStreak;

    @Column(name = "year_month")
    private YearMonth yearMonth;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserReadingStatistics(User user, YearMonth yearMonth) {
        this.user = user;
        this.yearMonth = yearMonth;
        this.readBookCount = 0L;
        this.readPageCount = 0L;
        this.readMinuteCount = 0L;
        this.longestReadMinutes = 0;
        this.mostPagesReadInSession = 0;
        this.currentReadingStreak = 0;
        this.longestReadingStreak = 0;
    }

    public void incrementReadBookCount() {
        this.readBookCount++;
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
        this.readPageCount += readPages;
        if (readPages > this.mostPagesReadInSession) {
            this.mostPagesReadInSession = readPages;
        }
    }

    public void addReadMinutes(Integer readMinutes) {
        this.readMinuteCount += readMinutes;
        if (readMinutes > this.longestReadMinutes) {
            this.longestReadMinutes = readMinutes;
        }
    }

}
