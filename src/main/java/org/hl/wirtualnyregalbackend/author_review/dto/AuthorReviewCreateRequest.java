package org.hl.wirtualnyregalbackend.author_review.dto;

import lombok.Getter;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.review.ReviewRequest;

@Getter
@Setter
public class AuthorReviewCreateRequest extends ReviewRequest {

    private Long authorId;

}
