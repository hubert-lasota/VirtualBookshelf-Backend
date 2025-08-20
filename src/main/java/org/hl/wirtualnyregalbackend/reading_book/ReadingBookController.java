package org.hl.wirtualnyregalbackend.reading_book;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.reading_book.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/reading-books")
@AllArgsConstructor
public class ReadingBookController {

    private final ReadingBookService readingBookService;


    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(#readingBookRequest.bookshelfId, 'BOOKSHELF', 'CREATE')")
    public ResponseEntity<ReadingBookResponse> createReadingBook(
        @RequestPart("readingBook")
        @Validated(CreateGroup.class)
        ReadingBookCreateRequest readingBookRequest,
        @RequestPart(value = "cover", required = false)
        MultipartFile cover,
        UriComponentsBuilder uriBuilder
    ) {
        ReadingBookResponse response = readingBookService.createReadingBook(readingBookRequest, cover);

        URI location = uriBuilder
            .path("/v1/reading-books/{bookId}")
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{readingBookId}")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'READ')")
    public ReadingBookResponse findReadingBook(@PathVariable Long readingBookId) {
        return readingBookService.findReadingBookById(readingBookId);
    }

    @GetMapping
    public ReadingBookListResponse findCurrentUserReadingBooks(
        @RequestParam(required = false)
        String query,
        @AuthenticationPrincipal
        User user
    ) {
        return readingBookService.findUserReadingBooks(user, query);
    }

    @PatchMapping("/{readingBookId}")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'DELETE')")
    public ReadingBookResponse updateReadingBook(
        @PathVariable
        Long readingBookId,
        @RequestBody
        @Validated(UpdateGroup.class)
        ReadingBookUpdateRequest readingBookUpdateRequest
    ) {
        return readingBookService.updateReadingBook(readingBookId, readingBookUpdateRequest);
    }

    @PatchMapping("/{readingBookId}/move")
    @PreAuthorize("hasPermission(#request.bookshelfId, 'BOOKSHELF', 'UPDATE')")
    public ReadingBookResponse moveReadingBook(@PathVariable Long readingBookId,
                                               @Valid @RequestBody MoveReadingBookRequest request) {
        return readingBookService.moveReadingBook(readingBookId, request.bookshelfId());
    }

    @PatchMapping("/{readingBookId}/change-status")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'UPDATE')")
    public ReadingBookResponse markReadingBookAsRead(@PathVariable Long readingBookId, @RequestBody ChangeStatusRequest body) {
        return readingBookService.changeReadingBookStatus(readingBookId, body.status());
    }

    @DeleteMapping("/{readingBookId}")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReadingBook(@PathVariable Long readingBookId) {
        readingBookService.deleteReadingBook(readingBookId);
    }

}
