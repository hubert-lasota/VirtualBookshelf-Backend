package org.hl.wirtualnyregalbackend.reading_statistics.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.security.entity.User;

@Entity
@Table(name = "book_length_statistics")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookLengthStatistics extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private BookLength length;

    @Column(name = "book_count")
    private Long bookCount;

    @Column(name = "read_book_count")
    private Long readBookCount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public BookLengthStatistics(BookLength length, User user) {
        this.length = length;
        this.bookCount = 0L;
        this.user = user;
    }

    public void incrementBookCount() {
        this.bookCount++;
    }

    public void incrementReadBookCount() {
        this.readBookCount++;
    }


}
