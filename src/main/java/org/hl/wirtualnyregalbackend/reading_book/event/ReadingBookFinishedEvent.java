package org.hl.wirtualnyregalbackend.reading_book.event;

import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;

public record ReadingBookFinishedEvent(ReadingBook readingBook) {
}
