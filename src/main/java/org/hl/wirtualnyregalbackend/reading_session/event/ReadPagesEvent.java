package org.hl.wirtualnyregalbackend.reading_session.event;

import org.hl.wirtualnyregalbackend.common.reading.PageRange;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.hl.wirtualnyregalbackend.reading_session.model.SessionReadingDurationRange;
import org.springframework.lang.Nullable;

import java.util.Objects;

// TODO should not set currentPage in ReadingBook -> delete addReadPages, setCurrentPage is valid -> session should change currentPage if session.pageRange.to > currentPage
public record ReadPagesEvent(
    Integer readPages,
    Integer readMinutes,
    Long userId,
    Long readingBookId,
    SessionReadingDurationRange durationRange
) {

    public static ReadPagesEvent from(ReadingSession session) {
        ReadingBook rb = session.getReadingBook();
        return new ReadPagesEvent(
            session.getPageRange().getReadPages(),
            session.getDurationRange().getReadMinutes(),
            rb.getBookshelf().getUser().getId(),
            rb.getId(),
            session.getDurationRange()
        );
    }

    @Nullable
    public static ReadPagesEvent ofDifference(PageRange oldPr,
                                              PageRange newPr,
                                              SessionReadingDurationRange oldDr,
                                              SessionReadingDurationRange newDr,
                                              ReadingSession session) {
        boolean pagesChanged = !Objects.equals(oldPr, newPr);
        boolean timeChanged = !Objects.equals(oldDr, newDr);

        if (!pagesChanged && !timeChanged) {
            return null;
        }

        Integer readPages = newPr.getReadPages() - oldPr.getReadPages();
        Integer readMinutes = newDr.getReadMinutes() - oldDr.getReadMinutes();

        ReadingBook rb = session.getReadingBook();
        return new ReadPagesEvent(
            readPages,
            readMinutes,
            rb.getBookshelf().getUser().getId(),
            rb.getId(),
            session.getDurationRange()
        );
    }

}
