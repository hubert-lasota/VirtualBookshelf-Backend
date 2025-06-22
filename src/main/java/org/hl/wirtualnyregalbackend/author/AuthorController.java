package org.hl.wirtualnyregalbackend.author;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.author.dto.AuthorMutationDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/authors")
@AllArgsConstructor
class AuthorController {

    private final AuthorService authorService;


    @PostMapping
    public ResponseEntity<?> createAuthor(@RequestBody @Validated(CreateGroup.class) AuthorMutationDto authorDto) {
        var response = authorService.createAuthor(authorDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> findAuthors(Pageable pageable) {
        var response = authorService.findAuthors(pageable);
        return ResponseEntity.ok(response);
    }

}
