package org.hl.wirtualnyregalbackend.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.AssertTrue;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookReadingStatus;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;

import java.util.List;

@NotAllFieldsNull
public class BookshelfBookUpdateDto extends BookshelfBookCreateDto {

    @JsonProperty
    private final Long id;

    public BookshelfBookUpdateDto(Integer currentPage,
                                  BookReadingStatus status,
                                  RangeDate rangeDate,
                                  List<BookshelfBookNoteDto> notes,
                                  BookWithIdDto book,
                                  Long id) {
        super(currentPage, status, rangeDate, notes, book);
        this.id = id;
    }

    @AssertTrue(message = "Provide either bookshelf book ID or book details.")
    public boolean isValid() {
        return id != null && book != null;
    }

    public Long getId() {
        return id;
    }

}
