package org.hl.wirtualnyregalbackend.infrastructure.book;

import org.hl.wirtualnyregalbackend.application.book.BookReadingDetails;

public interface BookReadingDetailsRepository {

    BookReadingDetails findByBookIdAndUserId(Long bookId, Long userId);

}
