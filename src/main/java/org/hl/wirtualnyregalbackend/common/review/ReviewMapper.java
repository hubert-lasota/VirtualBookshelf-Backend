package org.hl.wirtualnyregalbackend.common.review;

import org.hl.wirtualnyregalbackend.user.UserMapper;
import org.hl.wirtualnyregalbackend.user.dto.UserHeaderResponseDto;

public class ReviewMapper {

    private ReviewMapper() {
    }


    public static ReviewResponseDto toReviewResponseDto(Review review) {
        UserHeaderResponseDto user = UserMapper.toUserHeaderResponseDto(review.getUser());
        ReviewDto reviewDto = new ReviewDto(review.getRating(), review.getContent());
        return new ReviewResponseDto(review.getId(), reviewDto, user, review.getCreatedAt(), review.getUpdatedAt());
    }

}
