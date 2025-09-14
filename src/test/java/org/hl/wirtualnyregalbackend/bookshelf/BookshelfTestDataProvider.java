package org.hl.wirtualnyregalbackend.bookshelf;

import org.hl.wirtualnyregalbackend.bookshelf.dto.BookshelfRequest;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;

class BookshelfTestDataProvider {

    private BookshelfTestDataProvider() {
    }

    public static BookshelfRequest getBookshelfRequest() {
        return new BookshelfRequest("Bookshelf Valid Name", BookshelfType.TO_READ, "Valid Description");
    }


}
