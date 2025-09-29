package org.hl.wirtualnyregalbackend.reading_book;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.model.BookFilter;
import org.hl.wirtualnyregalbackend.reading_book.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/reading-books")
@AllArgsConstructor
public class ReadingBookController {

    private final ReadingBookQueryService query;
    private final ReadingBookCommandService command;


    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(#readingBookRequest.bookshelfId, 'BOOKSHELF', 'CREATE')")
    @ResponseStatus(HttpStatus.CREATED)
    public ReadingBookResponse createReadingBook(
        @RequestPart("readingBook")
        @Validated
        ReadingBookCreateRequest readingBookRequest,
        @RequestPart(value = "cover", required = false)
        MultipartFile cover
    ) {
        return command.createReadingBook(readingBookRequest, cover);
    }

    @PatchMapping("/{readingBookId}")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'DELETE')")
    public ReadingBookResponse updateReadingBook(
        @PathVariable
        Long readingBookId,
        @RequestBody
        @Validated
        ReadingBookUpdateRequest readingBookRequest
    ) {
        return command.updateReadingBook(readingBookId, readingBookRequest);
    }

    @PatchMapping("/{readingBookId}/move")
    @PreAuthorize("hasPermission(#request.bookshelfId, 'BOOKSHELF', 'UPDATE')")
    public ReadingBookResponse moveReadingBook(@PathVariable Long readingBookId,
                                               @Valid @RequestBody MoveReadingBookRequest request) {
        return command.moveReadingBook(readingBookId, request.bookshelfId());
    }

    @PatchMapping("/{readingBookId}/change-status")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'UPDATE')")
    public ReadingBookResponse markReadingBookAsRead(@PathVariable Long readingBookId, @RequestBody ChangeStatusRequest body) {
        return command.changeReadingBookStatus(readingBookId, body.status());
    }

    @DeleteMapping("/{readingBookId}")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReadingBook(@PathVariable Long readingBookId) {
        command.deleteReadingBook(readingBookId);
    }

    @GetMapping
    public ReadingBookListResponse findCurrentUserReadingBooks(@Valid BookFilter bookFilter, @AuthenticationPrincipal User user) {
        return query.findUserReadingBooks(user, bookFilter);
    }

}
