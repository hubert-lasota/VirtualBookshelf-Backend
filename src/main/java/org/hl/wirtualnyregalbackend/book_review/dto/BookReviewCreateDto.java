package org.hl.wirtualnyregalbackend.book_review.dto;

import lombok.Getter;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.review.ReviewDto;

@Getter
@Setter
public class BookReviewCreateDto extends ReviewDto {

    private Long bookId;

}
