package org.hl.wirtualnyregalbackend.infrastructure.book.dto;

import org.hl.wirtualnyregalbackend.infrastructure.user.dto.UserHeaderResponse;

public record BookRatingResponse(Long id,
                                 UserHeaderResponse user,
                                 Byte rating,
                                 String ratingJustification) {
}
