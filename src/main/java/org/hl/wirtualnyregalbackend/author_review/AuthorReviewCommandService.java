package org.hl.wirtualnyregalbackend.author_review;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.AuthorQueryService;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author_review.dto.AuthorReviewCreateRequest;
import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;
import org.hl.wirtualnyregalbackend.author_review.event.AuthorReviewCreatedOrUpdatedEvent;
import org.hl.wirtualnyregalbackend.author_review.event.AuthorReviewDeletedEvent;
import org.hl.wirtualnyregalbackend.author_review.exception.AuthorReviewAlreadyExistsException;
import org.hl.wirtualnyregalbackend.common.review.ReviewMapper;
import org.hl.wirtualnyregalbackend.common.review.ReviewRequest;
import org.hl.wirtualnyregalbackend.common.review.ReviewResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;

@Service
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
@Slf4j
class AuthorReviewCommandService {

    private final AuthorReviewRepository repository;
    private final AuthorReviewQueryService reviewQuery;
    private final AuthorQueryService authorQuery;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ReviewResponse createAuthorReview(AuthorReviewCreateRequest reviewDto, User user) {
        Long authorId = reviewDto.getAuthorId();
        Author author = authorQuery.findAuthorById(authorId);
        if (repository.existsByAuthorIdAndUserId(authorId, user.getId())) {
            log.warn("Review already exists for author '{}'", authorId);
            throw new AuthorReviewAlreadyExistsException("Review already exists for author '%d' and user '%d'".formatted(authorId, user.getId()));
        }
        AuthorReview authorReview = AuthorReviewMapper.toAuthorReview(reviewDto, author, user);
        repository.save(authorReview);
        log.info("AuthorReview created: {}", authorReview);
        eventPublisher.publishEvent(AuthorReviewCreatedOrUpdatedEvent.from(authorReview));
        Locale locale = LocaleContextHolder.getLocale();
        return ReviewMapper.toReviewResponse(authorReview, locale);
    }

    @Transactional
    public ReviewResponse updateAuthorReview(Long authorReviewId, ReviewRequest reviewRequest) {
        AuthorReview authorReview = reviewQuery.findAuthorReviewById(authorReviewId);
        Float rating = reviewRequest.getRating();
        if (rating != null) {
            authorReview.setRating(rating);
        }
        String content = reviewRequest.getContent();
        if (content != null) {
            authorReview.setContent(content);
        }

        eventPublisher.publishEvent(AuthorReviewCreatedOrUpdatedEvent.from(authorReview));
        Locale locale = LocaleContextHolder.getLocale();
        return ReviewMapper.toReviewResponse(authorReview, locale);
    }

    @Transactional
    public void deleteAuthorReview(Long authorReviewId) {
        AuthorReview authorReview = reviewQuery.findAuthorReviewById(authorReviewId);
        repository.delete(authorReview);
        eventPublisher.publishEvent(AuthorReviewDeletedEvent.from(authorReview));
    }

}
