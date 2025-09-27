package org.hl.wirtualnyregalbackend.author_review;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.AuthorHelper;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author_review.dto.AuthorReviewCreateRequest;
import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;
import org.hl.wirtualnyregalbackend.author_review.event.AuthorReviewCreatedOrUpdatedEvent;
import org.hl.wirtualnyregalbackend.author_review.event.AuthorReviewDeletedEvent;
import org.hl.wirtualnyregalbackend.author_review.exception.AuthorReviewAlreadyExistsException;
import org.hl.wirtualnyregalbackend.author_review.exception.AuthorReviewNotFoundException;
import org.hl.wirtualnyregalbackend.common.review.ReviewMapper;
import org.hl.wirtualnyregalbackend.common.review.ReviewPageResponse;
import org.hl.wirtualnyregalbackend.common.review.ReviewRequest;
import org.hl.wirtualnyregalbackend.common.review.ReviewResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@Slf4j
public class AuthorReviewService {

    private final AuthorReviewRepository authorReviewRepository;
    private final AuthorHelper authorHelper;
    private final ApplicationEventPublisher eventPublisher;


    @Transactional
    public ReviewResponse createAuthorReview(AuthorReviewCreateRequest reviewDto, User user) {
        Long authorId = reviewDto.getAuthorId();
        Author author = authorHelper.findAuthorById(authorId);
        if (authorReviewRepository.existsByAuthorIdAndUserId(authorId, user.getId())) {
            log.warn("Review already exists for author '{}'", authorId);
            throw new AuthorReviewAlreadyExistsException("Review already exists for author '%d' and user '%d'".formatted(authorId, user.getId()));
        }
        AuthorReview authorReview = AuthorReviewMapper.toAuthorReview(reviewDto, author, user);
        authorReviewRepository.save(authorReview);
        log.info("AuthorReview created: {}", authorReview);
        eventPublisher.publishEvent(AuthorReviewCreatedOrUpdatedEvent.from(authorReview));
        return ReviewMapper.toReviewResponse(authorReview);
    }

    @Transactional
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

        eventPublisher.publishEvent(AuthorReviewCreatedOrUpdatedEvent.from(authorReview));
        return ReviewMapper.toReviewResponse(authorReview);
    }

    @Transactional
    public void deleteAuthorReview(Long authorReviewId) {
        AuthorReview authorReview = findAuthorReviewById(authorReviewId);
        authorReviewRepository.delete(authorReview);
        eventPublisher.publishEvent(AuthorReviewDeletedEvent.from(authorReview));
    }

    public ReviewPageResponse findAuthorReviews(Long authorId, Pageable pageable) {
        Page<ReviewResponse> page = authorReviewRepository
            .findByAuthorId(authorId, pageable)
            .map(ReviewMapper::toReviewResponse);
        return ReviewPageResponse.from(page);
    }

    public boolean isAuthorReviewOwner(Long authorReviewId, User user) {
        return authorReviewRepository.isAuthor(authorReviewId, user.getId());
    }

    private AuthorReview findAuthorReviewById(Long id) throws AuthorReviewNotFoundException {
        Optional<AuthorReview> reviewOpt = id != null ? authorReviewRepository.findById(id) : Optional.empty();
        return reviewOpt.orElseThrow(() -> {
            log.warn("AuthorReview not found with ID: {}", id);
            return new AuthorReviewNotFoundException(id);
        });
    }


}
