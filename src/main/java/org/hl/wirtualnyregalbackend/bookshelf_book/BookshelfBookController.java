package org.hl.wirtualnyregalbackend.bookshelf_book;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/bookshelf-books")
@AllArgsConstructor
public class BookshelfBookController {

    private final BookshelfBookService bookshelfBookService;

}
