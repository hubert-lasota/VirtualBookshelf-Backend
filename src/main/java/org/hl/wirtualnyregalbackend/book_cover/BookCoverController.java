package org.hl.wirtualnyregalbackend.book_cover;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book_cover.entity.BookCoverBinary;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/book-covers")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BookCoverController {

    private final BookCoverService bookCoverService;

    @GetMapping(value = "/{coverId}", produces = "image/*")
    public ResponseEntity<byte[]> findBookCoverBinaryDataById(@PathVariable Long coverId) {
        BookCoverBinary cover = bookCoverService.findBookCoverBinaryByCoverId(coverId);
        return ResponseEntity
            .ok()
            .header("Content-Type", cover.getMimeType())
            .body(cover.getBinaryData());
    }

}
