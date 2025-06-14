package org.hl.wirtualnyregalbackend.author;

import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;
import org.hl.wirtualnyregalbackend.author.dto.AuthorResponseDto;
import org.hl.wirtualnyregalbackend.common.model.PageResponseDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/authors")
class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody @Validated(CreateGroup.class) AuthorMutationDto authorDto) {
        AuthorResponseDto response = authorService.createAuthor(authorDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> findAuthors(Pageable pageable) {
        PageResponseDto<?> responsePage = authorService.findAuthors(pageable);
        return ResponseEntity.ok(responsePage);
    }

}
