package org.hl.wirtualnyregalbackend.author;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.author.dto.AuthorRequest;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.author.entity.Author;
import org.hl.wirtualnyregalbackend.author_profile_picture.AuthorProfilePictureService;
import org.hl.wirtualnyregalbackend.author_profile_picture.entity.AuthorProfilePicture;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class AuthorCommandService {

    private final AuthorRepository repository;
    private final AuthorQueryService query;
    private final AuthorProfilePictureService profilePictureService;


    @Transactional
    public AuthorResponse createAuthor(AuthorRequest authorRequest, MultipartFile profilePictureFile) {
        log.info("Creating new author: {}", authorRequest);
        AuthorProfilePicture picture = profilePictureService.createAuthorProfilePicture(authorRequest.profilePictureUrl(), profilePictureFile);
        Author author = repository.save(AuthorMapper.toAuthor(authorRequest, picture));
        log.info("Author created with ID: {}", author.getId());
        return AuthorMapper.toAuthorResponse(author);
    }

    @Transactional
    public AuthorResponse updateAuthor(Long authorId, AuthorRequest authorRequest, MultipartFile profilePictureFile) {
        Author author = query.findAuthorById(authorId);
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

    @Transactional
    public Author findOrCreateAuthor(Long id, AuthorRequest authorRequest) {
        if (id != null) {
            return query.findAuthorById(id);
        }

        Author author = AuthorMapper.toAuthor(authorRequest);
        return repository.save(author);
    }

}
