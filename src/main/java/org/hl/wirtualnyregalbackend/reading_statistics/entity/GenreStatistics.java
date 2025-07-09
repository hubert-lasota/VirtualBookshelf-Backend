package org.hl.wirtualnyregalbackend.reading_statistics.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.security.entity.User;

@Entity
@Table(name = "genre_statistics")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class GenreStatistics extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Column(name = "genre_count")
    private Long genreCount;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;


    public void incrementGenreCount() {
        this.genreCount++;
    }

}
