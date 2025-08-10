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
    @PreAuthorize("hasPermission(#readingBookDto.bookshelfId, 'BOOKSHELF', 'CREATE')")
    public ResponseEntity<ReadingBookResponseDto> createReadingBook(
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
            .buildAndExpand(response.id())
            .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{readingBookId}")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'READ')")
    public ReadingBookResponseDto findReadingBook(@PathVariable Long readingBookId) {
        return readingBookService.findReadingBookById(readingBookId);
    }

    @GetMapping
    public ReadingBookListResponseDto findCurrentUserReadingBooks(
        @RequestParam(required = false)
        String query,
        @AuthenticationPrincipal
        User user
    ) {
        return readingBookService.findUserReadingBooks(user, query);
    }

    @PatchMapping("/{readingBookId}")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'DELETE')")
    public ReadingBookResponseDto updateReadingBook(
        @PathVariable
        Long readingBookId,
        @RequestBody
        @Validated(UpdateGroup.class)
        ReadingBookUpdateDto readingBookDto
    ) {
        return readingBookService.updateReadingBook(readingBookId, readingBookDto);
    }

    @PatchMapping("/{readingBookId}/move")
    @PreAuthorize("hasPermission(#request.bookshelfId, 'BOOKSHELF', 'UPDATE')")
    public ReadingBookResponseDto moveReadingBook(@PathVariable Long readingBookId,
                                                  @Valid @RequestBody MoveReadingBookDto request) {
        return readingBookService.moveReadingBook(readingBookId, request.bookshelfId());
    }

    @PatchMapping("/{readingBookId}/change-status")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'UPDATE')")
    public ReadingBookResponseDto markReadingBookAsRead(@PathVariable Long readingBookId, @RequestBody ChangeStatusRequestDto body) {
        return readingBookService.changeReadingBookStatus(readingBookId, body.status());
    }

    @DeleteMapping("/{readingBookId}")
    @PreAuthorize("hasPermission(#readingBookId, 'READING_BOOK', 'DELETE')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReadingBook(@PathVariable Long readingBookId) {
        readingBookService.deleteReadingBook(readingBookId);
    }

}
