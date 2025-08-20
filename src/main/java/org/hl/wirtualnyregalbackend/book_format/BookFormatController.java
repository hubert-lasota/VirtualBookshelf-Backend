package org.hl.wirtualnyregalbackend.book_format;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book_format.dto.BookFormatListResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/book-formats")
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class BookFormatController {

    private final BookFormatService bookFormatService;


    @GetMapping
    public BookFormatListResponse findBookFormats(@RequestParam(required = false) Boolean availableInBookshelf,
                                                  @AuthenticationPrincipal User user) {
        return bookFormatService.findBookFormats(availableInBookshelf, user);
    }

}
