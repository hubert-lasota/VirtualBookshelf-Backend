package org.hl.wirtualnyregalbackend.reading_statistics.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.security.entity.User;

@Entity
@Table(name = "book_length_statistics")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookLengthStatistics extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private BookLength length;

    @Column(name = "book_count")
    private Long bookCount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public void incrementBookCount() {
        this.bookCount++;
    }

}
