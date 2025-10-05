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

    private int goalValue;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Setter(AccessLevel.NONE)
    @Column(name = "total_participants")
    private long totalParticipants;


    public Challenge(String title,
                     String description,
                     ChallengeDurationRange durationRange,
                     ChallengeType type,
                     Integer goalValue,
                     Genre genre,
                     User user) {
        this.title = title;
        this.description = description;
        this.durationRange = durationRange;
        this.type = type;
        this.goalValue = goalValue;
        this.genre = genre;
        this.user = user;
        this.totalParticipants = 0L;
    }


    public void incrementTotalParticipants() {
        this.totalParticipants++;
    }

    public void decrementTotalParticipants() {
        this.totalParticipants--;
    }

}
