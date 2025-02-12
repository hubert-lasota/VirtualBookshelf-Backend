package org.hl.wirtualnyregalbackend.book.dao;

import org.hl.wirtualnyregalbackend.book.model.BookReadingDetails;

public interface BookReadingDetailsRepository {

    BookReadingDetails findByBookIdAndUserId(Long bookId, Long userId);

}
