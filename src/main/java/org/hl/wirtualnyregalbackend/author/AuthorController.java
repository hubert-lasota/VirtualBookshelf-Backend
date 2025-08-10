package org.hl.wirtualnyregalbackend.author;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.dto.AuthorDetailsResponseDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorPageResponseDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponseDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/authors")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorController {

    private final AuthorService authorService;


    @PostMapping
    public ResponseEntity<AuthorResponseDto> createAuthor(@RequestBody
                                                          @Validated(CreateGroup.class)
                                                          AuthorMutationDto authorDto,
                                                          UriComponentsBuilder uriBuilder) {
        AuthorResponseDto response = authorService.createAuthor(authorDto);

        URI location = uriBuilder
            .path("/v1/authors/{id}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{authorId}")
    public AuthorDetailsResponseDto findAuthorDetailsById(@PathVariable Long authorId) {
        return authorService.findAuthorDetailsById(authorId);
    }

    @GetMapping
    public AuthorPageResponseDto findAuthors(@RequestParam(required = false) Boolean availableInBookshelf,
                                             @AuthenticationPrincipal User user,
                                             Pageable pageable) {
        return authorService.findAuthors(availableInBookshelf, user, pageable);
    }

}
