package org.hl.wirtualnyregalbackend.reading_statistics.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;

import java.time.YearMonth;

@Entity
@Table(name = "genre_statistics")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GenreStatistics extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column(name = "book_count")
    private Long bookCount;

    @Column(name = "read_book_count")
    private Long readBookCount;

    @Column(name = "year_month")
    private YearMonth yearMonth;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    public GenreStatistics(Genre genre, User user, YearMonth yearMonth) {
        this.genre = genre;
        this.user = user;
        this.yearMonth = yearMonth;
        this.bookCount = 0L;
        this.readBookCount = 0L;
    }


    public void incrementBookCount() {
        this.bookCount++;
    }

    public void incrementReadBookCount() {
        this.readBookCount++;
    }

}
