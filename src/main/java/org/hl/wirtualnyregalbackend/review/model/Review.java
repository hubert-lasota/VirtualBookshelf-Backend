package org.hl.wirtualnyregalbackend.review.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@MappedSuperclass
public abstract class Review extends BaseEntity {

    @Column
    private Float rating;

    @Column
    private String content;

    protected Review() {
    }

    public Review(Float rating, String content) {
        this.rating = rating;
        this.content = content;
    }


    public void updateRating(Float rating) {
        if (rating != null) {
            this.rating = rating;
        }
    }

    public void updateContent(String content) {
        if (content != null) {
            this.content = content;
        }
    }

    public Float getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

}
