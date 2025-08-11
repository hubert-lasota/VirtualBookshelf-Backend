package org.hl.wirtualnyregalbackend.common.review;

import org.hl.wirtualnyregalbackend.user.UserMapper;
import org.hl.wirtualnyregalbackend.user.dto.UserResponseDto;

public class ReviewMapper {

    private ReviewMapper() {
    }


    public static ReviewResponseDto toReviewResponseDto(Review review) {
        UserResponseDto user = UserMapper.toUserResponseDto(review.getUser());
        ReviewDto reviewDto = new ReviewDto(review.getRating(), review.getContent());
        return new ReviewResponseDto(review.getId(), reviewDto, user, review.getCreatedAt(), review.getUpdatedAt());
    }

}
