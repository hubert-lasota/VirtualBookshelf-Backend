package org.hl.wirtualnyregalbackend.book_cover;

import org.hl.wirtualnyregalbackend.book_cover.entity.BookCoverBinary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/book-covers")
class BookCoverController {

    private final BookCoverService bookCoverService;

    public BookCoverController(BookCoverService bookCoverService) {
        this.bookCoverService = bookCoverService;
    }

    @GetMapping(value = "/{id}", produces = "image/*")
    public ResponseEntity<?> findBookCoverById(@PathVariable Long id) {
        BookCoverBinary binary = bookCoverService.findBookCoverBinaryByCoverId(id);
        return ResponseEntity.ok()
            .header("Content-Type", binary.getMimeType())
            .body(binary);
    }

}
