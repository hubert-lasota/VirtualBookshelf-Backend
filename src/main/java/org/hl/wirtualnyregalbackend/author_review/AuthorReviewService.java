package org.hl.wirtualnyregalbackend.author_review;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.AuthorHelper;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author_review.dto.AuthorReviewCreateRequest;
import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.review.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
public class AuthorReviewService {

    private final AuthorReviewRepository authorReviewRepository;
    private final AuthorHelper authorHelper;


    public ReviewResponse createAuthorReview(AuthorReviewCreateRequest reviewDto, User user) {
        Long authorId = reviewDto.getAuthorId();
        Author author = authorHelper.findAuthorById(authorId);
        if (authorReviewRepository.existsByAuthorIdAndUserId(authorId, user.getId())) {
            throw new InvalidRequestException("Review already exists for author '%d' and user '%d'".formatted(authorId, user.getId()));
        }
        AuthorReview authorReview = AuthorReviewMapper.toAuthorReview(reviewDto, author, user);
        authorReviewRepository.save(authorReview);
        return ReviewMapper.toReviewResponse(authorReview);
    }

    public ReviewResponse updateAuthorReview(Long authorReviewId, ReviewRequest reviewRequest) {
        AuthorReview authorReview = findAuthorReviewById(authorReviewId);
        Float rating = reviewRequest.getRating();
        if (rating != null) {
            authorReview.setRating(rating);
        }
        String content = reviewRequest.getContent();
        if (content != null) {
            authorReview.setContent(content);
        }
        authorReviewRepository.save(authorReview);
        return ReviewMapper.toReviewResponse(authorReview);
    }

    public void deleteAuthorReview(Long authorReviewId) {
        AuthorReview authorReview = findAuthorReviewById(authorReviewId);
        authorReviewRepository.delete(authorReview);
    }

    public ReviewStatistics getAuthorReviewStats(Long authorId) {
        return authorReviewRepository
            .getReviewStatsByAuthorId(authorId)
            .orElse(new ReviewStatistics(authorId, 0D, 0L));
    }

    public ReviewPageResponse findAuthorReviews(Long authorId, Pageable pageable) {
        Page<ReviewResponse> page = authorReviewRepository
            .findByAuthorId(authorId, pageable)
            .map(ReviewMapper::toReviewResponse);
        return ReviewPageResponse.from(page);
    }

    public boolean isAuthor(Long authorReviewId, Long userId) {
        return authorReviewRepository.isAuthor(authorReviewId, userId);
    }

    private AuthorReview findAuthorReviewById(Long id) throws EntityNotFoundException {
        return authorReviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("AuthorReview not found for id: %d".formatted(id)));
    }

    @Nullable
    public AuthorReview findAuthorReviewEntityById(Long id) {
        Optional<AuthorReview> reviewOpt = id != null ? authorReviewRepository.findById(id) : Optional.empty();
        return reviewOpt.orElse(null);
    }

}
