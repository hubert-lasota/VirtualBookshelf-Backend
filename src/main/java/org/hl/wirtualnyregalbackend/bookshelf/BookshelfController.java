package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfCreateDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfResponseDto;
import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfUpdateDto;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/v1/bookshelves")
class BookshelfController {

    private final BookshelfService bookshelfService;

    public BookshelfController(BookshelfService bookshelfService) {
        this.bookshelfService = bookshelfService;
    }


    @PostMapping
    public ResponseEntity<?> createBookshelf(@Validated(CreateGroup.class) @RequestBody BookshelfCreateDto bookshelfCreateDto,
                                             @AuthenticationPrincipal User user) {
        BookshelfResponseDto response = bookshelfService.createBookshelf(bookshelfCreateDto, user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findCurrentUserBookshelves(@AuthenticationPrincipal User user) {
        Collection<BookshelfResponseDto> bookshelves = bookshelfService.findUserBookshelves(user.getId());
        Map<String, Object> response = Map.of("bookshelves", bookshelves);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{id}")
    // TODO add PreAuthorize for bookshelf owner
    public ResponseEntity<?> updateBookshelf(@PathVariable Long id,
                                             @RequestBody @Validated(UpdateGroup.class) BookshelfUpdateDto bookshelfUpdateDto) {
        return ResponseEntity.ok(bookshelfService.updateBookshelf(id, bookshelfUpdateDto));
    }


//    @PostMapping("/{bookshelfId}/books")
//    @PreAuthorize("hasPermission(#bookshelfId, 'BOOKSHELF', 'DELETE')")
//    public ResponseEntity<?> createBookshelfBook(@PathVariable Long bookshelfId,
//                                                 @RequestBody @Validated(CreateGroup.class) BookshelfBookMutationDto bookshelfBookDto,
//                                                 @AuthenticationPrincipal User user) {
//        BookshelfBookResponseDto response = bookshelfService.createBookshelfBook(bookshelfId, bookshelfBookDto, user);
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }
//
//    @PatchMapping("/{bookshelfId}/books/{bookshelfBookId}")
//    @PreAuthorize("hasPermission(#bookshelfId, 'BOOKSHELF', 'DELETE')")
//    public ResponseEntity<?> updateBookshelfBook(@PathVariable Long bookshelfId,
//                                                 @PathVariable Long bookshelfBookId,
//                                                 @RequestBody @Validated(UpdateGroup.class) BookshelfBookMutationDto bookshelfBookDto) {
//        BookshelfBookResponseDto response = bookshelfService.updateBookshelfBook(bookshelfId, bookshelfBookId, bookshelfBookDto);
//        return ResponseEntity.ok(response);
//    }
//
//    @DeleteMapping("/{bookshelfId}/books/{bookId}")
//    @PreAuthorize("hasPermission(#bookshelfId, 'BOOKSHELF', 'DELETE')")
//    public ResponseEntity<?> deleteBookshelfBook(@PathVariable Long bookshelfId,
//                                                 @PathVariable Long bookId) {
//        bookshelfService.deleteBookshelfBook(bookshelfId, bookId);
//        return ResponseEntity.noContent().build();
//    }

}
