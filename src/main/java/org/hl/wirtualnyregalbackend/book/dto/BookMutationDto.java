package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;

import java.util.List;
import java.util.Locale;

@NotAllFieldsNull(groups = UpdateGroup.class)
public class BookMutationDto extends BaseBookDto {

    @JsonProperty
    @NotNull(groups = CreateGroup.class)
    private final Long formatId;

    public BookMutationDto(String isbn,
                           String title,
                           Integer publicationYear,
                           Integer pageCount,
                           Locale language,
                           String description,
                           String coverUrl,
                           PublisherWithIdDto publisher,
                           List<AuthorWithIdDto> authors,
                           List<GenreWithIdDto> genres,
                           List<BookSeriesAssignmentDto> series,
                           Long formatId) {
        super(isbn, title, publicationYear, pageCount, language, description, coverUrl, publisher, authors, genres, series);
        this.formatId = formatId;
    }

    public Long getFormatId() {
        return formatId;
    }

}
