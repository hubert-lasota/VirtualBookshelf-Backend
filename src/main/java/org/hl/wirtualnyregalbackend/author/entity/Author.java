package org.hl.wirtualnyregalbackend.author.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.author_profile_picture.entity.AuthorProfilePicture;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "author")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class Author extends BaseEntity {

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "description")
    private String description;

    @Column(name = "total_reviews")
    @Setter(AccessLevel.NONE)
    private int totalReviews;

    @Column(name = "average_rating")
    private double averageRating;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "author_profile_picture_id")
    private AuthorProfilePicture authorProfilePicture;


    public Author(String fullName, String description, AuthorProfilePicture authorProfilePicture) {
        this.fullName = fullName;
        this.description = description;
        this.authorProfilePicture = authorProfilePicture;
        this.totalReviews = 0;
        this.averageRating = 0.0;
    }


    public void incrementTotalReviews() {
        this.totalReviews++;
    }

    public void decrementTotalReviews() {
        this.totalReviews--;
    }

}
