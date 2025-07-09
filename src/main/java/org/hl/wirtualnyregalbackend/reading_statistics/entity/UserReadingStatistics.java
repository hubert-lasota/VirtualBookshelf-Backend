package org.hl.wirtualnyregalbackend.reading_statistics.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.security.entity.User;

@Entity
@Table(name = "user_reading_statistics")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
public class UserReadingStatistics extends BaseEntity {

    @Column(name = "total_read_books")
    private Long totalReadBooks;

    @Column(name = "total_read_pages")
    private Long totalReadPages;

    @Column(name = "total_read_minutes")
    private Long totalReadMinutes;

    @Column(name = "longest_read_minutes")
    private Long longestReadMinutes;

    @Column(name = "current_reading_streak")
    private Integer currentReadingStreak;

    @Column(name = "longest_reading_streak")
    private Integer longestReadingStreak;

    @Setter(AccessLevel.NONE)
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
