package org.hl.wirtualnyregalbackend.challenge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeDurationRange;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeType;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString(callSuper = true)
public class Challenge extends BaseEntity {

    @Column
    private String title;

    @Column
    private String description;

    @Embedded
    private ChallengeDurationRange durationRange;

    @Column
    @Enumerated(EnumType.STRING)
    private ChallengeType type;

    private Integer goalValue;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
