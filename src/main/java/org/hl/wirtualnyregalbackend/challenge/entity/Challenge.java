package org.hl.wirtualnyregalbackend.challenge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeType;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.genre.entity.Genre;

import java.time.Instant;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Challenge extends BaseEntity {

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private Instant startAt;

    @Column
    private Instant endAt;

    @Column
    @Enumerated(EnumType.STRING)
    private ChallengeType type;

    private Integer targetCount;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
