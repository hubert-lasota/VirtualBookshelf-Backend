package org.hl.wirtualnyregalbackend.book_review.dto;

import lombok.Getter;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.review.ReviewRequest;

@Getter
@Setter
public class BookReviewCreateRequest extends ReviewRequest {

    private Long bookId;

}
