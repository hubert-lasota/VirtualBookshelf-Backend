package org.hl.wirtualnyregalbackend.recommendation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.entity.Author;

@Entity
@Table(name = "author_recommendation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthorRecommendation extends BaseRecommendation {

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    public AuthorRecommendation(User user, Author author) {
        super(user, 0F);
        this.author = author;
    }

}
