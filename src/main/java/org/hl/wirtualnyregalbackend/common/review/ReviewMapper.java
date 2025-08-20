package org.hl.wirtualnyregalbackend.common.review;

import org.hl.wirtualnyregalbackend.user.UserMapper;
import org.hl.wirtualnyregalbackend.user.dto.UserResponse;

public class ReviewMapper {

    private ReviewMapper() {
    }


    public static ReviewResponse toReviewResponse(Review review) {
        UserResponse user = UserMapper.toUserResponse(review.getUser());
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
