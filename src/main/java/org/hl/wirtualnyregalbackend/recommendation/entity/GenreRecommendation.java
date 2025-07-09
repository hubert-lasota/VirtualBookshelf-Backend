package org.hl.wirtualnyregalbackend.recommendation.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;
import org.hl.wirtualnyregalbackend.security.entity.User;

@Entity
@Table(name = "genre_recommendation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class GenreRecommendation extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Setter
    @Column
    private Float score;

}
