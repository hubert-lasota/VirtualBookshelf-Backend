package org.hl.wirtualnyregalbackend.book_reading;

import org.hl.wirtualnyregalbackend.book_reading.model.BookReadingDetails;
import org.hl.wirtualnyregalbackend.book_reading.model.BookReadingStatus;
import org.hl.wirtualnyregalbackend.book_reading.model.dto.BookReadingDetailsResponse;

public class BookReadingDetailsMapper {

    private BookReadingDetailsMapper() {
    }


    public static BookReadingDetailsResponse toBookReadingDetailsResponse(BookReadingDetails readingDetails) {
        BookReadingStatus status = readingDetails.getFinishedAt() == null ? BookReadingStatus.READING : BookReadingStatus.FINISHED;
        return new BookReadingDetailsResponse(
            readingDetails.getCurrentPage(),
            readingDetails.getProgressPercentage(),
            status,
            readingDetails.getStartedAt(),
            readingDetails.getFinishedAt()
        );
    }
}
