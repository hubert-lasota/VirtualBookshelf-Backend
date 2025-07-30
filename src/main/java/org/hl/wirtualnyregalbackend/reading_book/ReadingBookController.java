package org.hl.wirtualnyregalbackend.reading_book;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;
import org.hl.wirtualnyregalbackend.reading_book.dto.*;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/v1/reading-books")
@AllArgsConstructor
public class ReadingBookController {

    private final ReadingBookService readingBookService;


    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasPermission(#readingBookDto.bookshelfId, 'BOOKSHELF', 'CREATE')")
    public ResponseEntity<?> createReadingBook(
        @RequestPart("readingBook")
        @Validated(CreateGroup.class)
        ReadingBookCreateDto readingBookDto,
        @RequestPart(value = "cover", required = false)
        MultipartFile cover,
        UriComponentsBuilder uriBuilder
    ) {
        ReadingBookResponseDto response = readingBookService.createReadingBook(readingBookDto, cover);

        URI location = uriBuilder
            .path("/v1/reading-books/{bookId}")
            .buildAndExpand(response.getId())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{readingBookId}")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'READ')")
    public ResponseEntity<?> findReadingBook(@PathVariable Long readingBookId) {
        var response = readingBookService.findReadingBookById(readingBookId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> findCurrentUserReadingBooks(
        @RequestParam(required = false)
        String query,
        @AuthenticationPrincipal
        User user
    ) {
        var response = readingBookService.findUserReadingBooks(user, query);
        Map<String, Object> responseMap = Map.of("readingBooks", response);
        return ResponseEntity.ok(responseMap);
    }

    @PatchMapping("/{readingBookId}")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'DELETE')")
    public ResponseEntity<?> updateReadingBook(
        @PathVariable
        Long readingBookId,
        @RequestBody
        @Validated(UpdateGroup.class)
        ReadingBookMutationDto readingBookDto
    ) {
        var response = readingBookService.updateReadingBook(readingBookId, readingBookDto);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{readingBookId}/move")
    @PreAuthorize("hasPermission(#request.bookshelfId, 'BOOKSHELF', 'UPDATE')")
    public ResponseEntity<?> moveReadingBook(@PathVariable Long readingBookId,
                                             @Valid @RequestBody MoveReadingBookDto request) {
        var response = readingBookService.moveReadingBook(readingBookId, request.bookshelfId());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{readingBookId}/change-status")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'UPDATE')")
    public ResponseEntity<?> markReadingBookAsRead(@PathVariable Long readingBookId, ChangeStatusRequestDto body) {
        var response = readingBookService.changeReadingBookStatus(readingBookId, body.status());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{readingBookId}")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'DELETE')")
    public ResponseEntity<?> deleteReadingBook(@PathVariable Long readingBookId) {
        readingBookService.deleteReadingBook(readingBookId);
        return ResponseEntity.noContent().build();
    }

}
