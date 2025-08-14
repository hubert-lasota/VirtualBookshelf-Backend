package org.hl.wirtualnyregalbackend.author_review.dto;

import lombok.Getter;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.review.ReviewDto;

@Getter
@Setter
public class AuthorReviewCreateDto extends ReviewDto {

    private Long authorId;

}
