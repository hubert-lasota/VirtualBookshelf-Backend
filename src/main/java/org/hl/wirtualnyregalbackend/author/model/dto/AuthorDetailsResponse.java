package org.hl.wirtualnyregalbackend.author.model.dto;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.hl.wirtualnyregalbackend.book.model.dto.response.BookResponseDto;
import org.hl.wirtualnyregalbackend.review.model.dto.ReviewResponseDto;
import org.hl.wirtualnyregalbackend.tag.model.dto.TagDto;
import org.springframework.data.domain.Page;

import java.util.Collection;

public record AuthorDetailsResponse(
    @JsonUnwrapped AuthorResponse authorResponse,
    Page<BookResponseDto> bookPage,
    Page<ReviewResponseDto> reviewPage,
    Float averageRating,
    Collection<TagDto> tags
) {
}
