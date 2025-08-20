package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.dto.AuthorDetailsResponse;
import org.hl.wirtualnyregalbackend.author.dto.AuthorRequest;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author.entity.AuthorProfilePicture;
import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;
import org.hl.wirtualnyregalbackend.common.review.ReviewMapper;
import org.hl.wirtualnyregalbackend.common.review.ReviewResponse;
import org.hl.wirtualnyregalbackend.common.review.ReviewStatistics;
import org.springframework.lang.Nullable;

public class AuthorMapper {

    private AuthorMapper() {
    }

    public static AuthorResponse toAuthorResponse(Author author) {
        return new AuthorResponse(
            author.getId(),
            author.getFullName(),
            getPhotoUrl(author)
        );
    }

    public static AuthorDetailsResponse toAuthorDetailsResponse(Author author,
                                                                ReviewStatistics reviewStats,
                                                                @Nullable AuthorReview review) {
        ReviewResponse reviewResponse = review != null ? ReviewMapper.toReviewResponse(review) : null;
        return new AuthorDetailsResponse(
            author.getId(),
            author.getFullName(),
            getPhotoUrl(author),
            author.getDescription(),
            reviewStats,
            reviewResponse,
            author.getCreatedAt(),
            author.getUpdatedAt()
        );
    }

    private static String getPhotoUrl(Author author) {
        AuthorProfilePicture profilePicture = author.getAuthorProfilePicture();
        return profilePicture != null ? profilePicture.getUrl() : null;
    }

    public static Author toAuthor(AuthorRequest authorDto) {
        String photoUrl = authorDto.photoUrl();
        AuthorProfilePicture authorProfilePicture = photoUrl != null ? new AuthorProfilePicture(photoUrl) : null;
        return new Author(authorDto.fullName(), authorDto.description(), authorProfilePicture);
    }

}
