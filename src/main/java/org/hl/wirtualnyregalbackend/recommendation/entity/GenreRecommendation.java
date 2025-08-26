package org.hl.wirtualnyregalbackend.recommendation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;

@Entity
@Table(name = "genre_recommendation")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Getter
public class GenreRecommendation extends BaseRecommendation {

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public GenreRecommendation(User user, Float score, Genre genre) {
        super(user, score);
        this.genre = genre;
    }

}
