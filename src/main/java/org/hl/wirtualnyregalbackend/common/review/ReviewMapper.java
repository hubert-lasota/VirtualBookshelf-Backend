package org.hl.wirtualnyregalbackend.common.review;

import org.hl.wirtualnyregalbackend.user.UserMapper;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;

import java.util.Locale;

public class ReviewMapper {

    private ReviewMapper() {
    }


    public static ReviewResponse toReviewResponse(Review review, Locale locale) {
        UserResponse user = UserMapper.toUserResponse(review.getUser(), locale);
        return new ReviewResponse(
            review.getId(),
            review.getRating(),
            review.getContent(),
            user,
            review.getCreatedAt(),
            review.getUpdatedAt()
        );
    }

}
