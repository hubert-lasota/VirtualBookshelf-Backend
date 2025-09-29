package org.hl.wirtualnyregalbackend.author;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.dto.AuthorDetailsResponse;
import org.hl.wirtualnyregalbackend.author.dto.AuthorPageResponse;
import org.hl.wirtualnyregalbackend.author.dto.AuthorRequest;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponse;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/authors")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorController {

    private final AuthorCommandService command;
    private final AuthorQueryService query;


    @PostMapping
    public ResponseEntity<AuthorResponse> createAuthor(@RequestPart("author")
                                                       @Validated(CreateGroup.class)
                                                       AuthorRequest authorRequest,
                                                       @RequestPart(value = "profilePicture", required = false)
                                                       MultipartFile profilePicture,
                                                       UriComponentsBuilder uriBuilder) {
        AuthorResponse response = command.createAuthor(authorRequest, profilePicture);

        URI location = uriBuilder
            .path("/v1/authors/{id}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PatchMapping("/{authorId}")
    public AuthorResponse updateAuthor(@PathVariable Long authorId,
                                       @RequestPart("author")
                                       @Validated(UpdateGroup.class)
                                       AuthorRequest authorRequest,
                                       @RequestPart("profilePicture")
                                       MultipartFile profilePicture) {
        return command.updateAuthor(authorId, authorRequest, profilePicture);
    }

    @GetMapping("/{authorId}")
    public AuthorDetailsResponse findAuthorDetailsById(@PathVariable Long authorId) {
        return query.findAuthorDetailsById(authorId);
    }

    @GetMapping
    public AuthorPageResponse findAuthors(@RequestParam(required = false) Boolean availableInBookshelf,
                                          @AuthenticationPrincipal User user,
                                          Pageable pageable) {
        return query.findAuthors(availableInBookshelf, user, pageable);
    }

}
