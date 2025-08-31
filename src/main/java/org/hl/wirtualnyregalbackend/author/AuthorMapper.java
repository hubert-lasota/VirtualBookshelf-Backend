package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.dto.AuthorDetailsResponse;
import org.hl.wirtualnyregalbackend.author.dto.AuthorRequest;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author_profile_picture.entity.AuthorProfilePicture;
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
            getPictureUrl(author)
        );
    }

    public static AuthorDetailsResponse toAuthorDetailsResponse(Author author,
                                                                ReviewStatistics reviewStats,
                                                                @Nullable AuthorReview review) {
        ReviewResponse reviewResponse = review != null ? ReviewMapper.toReviewResponse(review) : null;
        return new AuthorDetailsResponse(
            author.getId(),
            author.getFullName(),
            getPictureUrl(author),
            author.getDescription(),
            reviewStats,
            reviewResponse,
            author.getCreatedAt(),
            author.getUpdatedAt()
        );
    }

    private static String getPictureUrl(Author author) {
        AuthorProfilePicture profilePicture = author.getAuthorProfilePicture();
        return profilePicture != null ? profilePicture.getUrl() : null;
    }

    public static Author toAuthor(AuthorRequest authorRequest) {
        String picUrl = authorRequest.profilePictureUrl();
        AuthorProfilePicture authorProfilePicture = picUrl != null ? new AuthorProfilePicture(picUrl) : null;
        return toAuthor(authorRequest, authorProfilePicture);
    }

    public static Author toAuthor(AuthorRequest authorRequest, AuthorProfilePicture profilePicture) {
        return new Author(authorRequest.fullName(), authorRequest.description(), profilePicture);
    }

}
