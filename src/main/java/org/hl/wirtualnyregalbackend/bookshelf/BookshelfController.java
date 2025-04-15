package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfDto;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfResponse;
import org.hl.wirtualnyregalbackend.bookshelf.model.dto.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.security.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/v1/bookshelves")
public class BookshelfController {

    private final BookshelfService bookshelfService;

    public BookshelfController(BookshelfService bookshelfService) {
        this.bookshelfService = bookshelfService;
    }

    @PostMapping
    public ResponseEntity<?> createBookshelfForCurrentUser(@Validated(CreateGroup.class) @RequestBody BookshelfDto bookshelfDto,
                                                           @AuthenticationPrincipal User user) {
        BookshelfResponseDto response = bookshelfService.createBookshelf(bookshelfDto, user);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> findCurrentUserBookshelves(@AuthenticationPrincipal User user) {
        Collection<BookshelfResponse> bookshelves = bookshelfService.findUserBookshelves(user.getId());
        Map<String, Object> response = Map.of("bookshelves", bookshelves);
        return ResponseEntity.ok(response);
    }

}
