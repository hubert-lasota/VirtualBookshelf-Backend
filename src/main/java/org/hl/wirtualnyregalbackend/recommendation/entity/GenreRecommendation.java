package org.hl.wirtualnyregalbackend.recommendation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;

@Entity
@Table(name = "genre_recommendation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GenreRecommendation extends BaseRecommendation {

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public GenreRecommendation(User user, Genre genre) {
        super(user, 0F);
        this.genre = genre;
    }

}
