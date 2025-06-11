package org.hl.wirtualnyregalbackend.book_format;

import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/book-formats")
class BookFormatController {

    private final BookFormatService bookFormatService;

    public BookFormatController(BookFormatService bookFormatService) {
        this.bookFormatService = bookFormatService;
    }


    @GetMapping
    public ResponseEntity<?> findBookFormats() {
        List<BookFormatDto> formats = bookFormatService.findBookFormats();
        Map<String, List<BookFormatDto>> response = Map.of("formats", formats);
        return ResponseEntity.ok(response);
    }

}
