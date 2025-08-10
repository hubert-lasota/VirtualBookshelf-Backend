package org.hl.wirtualnyregalbackend.author;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorPageResponseDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponseDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/authors")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorController {

    private final AuthorService authorService;


    @PostMapping
    public AuthorResponseDto createAuthor(@RequestBody @Validated(CreateGroup.class) AuthorMutationDto authorDto) {
        return authorService.createAuthor(authorDto);
    }

    @GetMapping
    public AuthorPageResponseDto findAuthors(@RequestParam(required = false) Boolean availableInBookshelf,
                                             @AuthenticationPrincipal User user,
                                             Pageable pageable) {
        return authorService.findAuthors(availableInBookshelf, user, pageable);
    }

}
