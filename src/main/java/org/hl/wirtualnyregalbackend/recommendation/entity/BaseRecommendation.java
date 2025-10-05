package org.hl.wirtualnyregalbackend.recommendation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BaseRecommendation extends BaseEntity {

    protected static final float SCORE_INCREMENT = 0.1F;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private float score;


    public void boostScore() {
        this.score += SCORE_INCREMENT;
    }

    public void reduceScore() {
        float newScore = this.score - SCORE_INCREMENT;
        this.score = newScore < 0 ? 0F : newScore;
    }

}
