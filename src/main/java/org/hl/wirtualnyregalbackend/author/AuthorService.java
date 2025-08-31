package org.hl.wirtualnyregalbackend.author;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.dto.AuthorDetailsResponse;
import org.hl.wirtualnyregalbackend.author.dto.AuthorPageResponse;
import org.hl.wirtualnyregalbackend.author.dto.AuthorRequest;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author.exception.AuthorNotFoundException;
import org.hl.wirtualnyregalbackend.author_profile_picture.AuthorProfilePictureService;
import org.hl.wirtualnyregalbackend.author_profile_picture.entity.AuthorProfilePicture;
import org.hl.wirtualnyregalbackend.author_review.AuthorReviewService;
import org.hl.wirtualnyregalbackend.author_review.entity.AuthorReview;
import org.hl.wirtualnyregalbackend.common.review.ReviewStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorProfilePictureService profilePictureService;
    private final AuthorReviewService reviewService;


    @Transactional
    public AuthorResponse createAuthor(AuthorRequest authorRequest, MultipartFile profilePictureFile) {
        log.info("Creating new author: {}", authorRequest);
        AuthorProfilePicture picture = profilePictureService.createAuthorProfilePicture(authorRequest.profilePictureUrl(), profilePictureFile);
        Author author = AuthorMapper.toAuthor(authorRequest, picture);
        log.info("Author created with ID: {}", author.getId());
        return AuthorMapper.toAuthorResponse(author);
    }

    public AuthorResponse updateAuthor(Long authorId, AuthorRequest authorRequest, MultipartFile profilePictureFile) {
        Author author = findAuthorById(authorId);
        log.info("Updating author: {} by request: {}", author, authorRequest);

        String fullName = authorRequest.fullName();
        if (fullName != null) {
            author.setFullName(fullName);
        }
        String profilePictureUrl = authorRequest.profilePictureUrl();
        if (profilePictureUrl != null || profilePictureFile != null) {
            author.setAuthorProfilePicture(profilePictureService.createAuthorProfilePicture(profilePictureUrl, profilePictureFile));
        }

        String description = authorRequest.description();
        if (description != null) {
            author.setDescription(description);
        }

        log.info("Author updated: {}", author);
        return AuthorMapper.toAuthorResponse(author);
    }

    public AuthorDetailsResponse findAuthorDetailsById(Long authorId) {
        Author author = findAuthorById(authorId);
        ReviewStatistics stats = reviewService.getAuthorReviewStats(authorId);
        AuthorReview review = reviewService.findOptionalAuthorReviewById(authorId).orElse(null);
        return AuthorMapper.toAuthorDetailsResponse(author, stats, review);
    }

    public AuthorPageResponse findAuthors(Boolean availableInBookshelf,
                                          User user,
                                          Pageable pageable) {
        Specification<Author> spec = availableInBookshelf != null
            ? AuthorSpecification.availableInBookshelf(availableInBookshelf, user)
            : Specification.where(null);

        Page<AuthorResponse> authorPage = authorRepository
            .findAll(spec, pageable)
            .map(AuthorMapper::toAuthorResponse);
        return AuthorPageResponse.from(authorPage);
    }

    public Author findOrCreateAuthor(Long id, AuthorRequest authorRequest) {
        if (id != null) {
            return findAuthorById(id);
        }

        return createAuthorEntity(authorRequest);
    }

    private Author createAuthorEntity(AuthorRequest authorRequest) {
        Author author = AuthorMapper.toAuthor(authorRequest);
        return authorRepository.save(author);
    }

    private Author findAuthorById(Long id) throws AuthorNotFoundException {
        Optional<Author> authorOpt = id != null ? authorRepository.findById(id) : Optional.empty();
        return authorOpt.orElseThrow(() -> {
            log.warn("Author not found with ID: {}", id);
            return new AuthorNotFoundException(id);
        });
    }

}
