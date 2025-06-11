package org.hl.wirtualnyregalbackend.bookshelf.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.hl.wirtualnyregalbackend.bookshelf.entity.BookshelfType;

import java.time.Instant;
import java.util.List;

@JsonPropertyOrder({"id"})
public class BookshelfResponseDto extends BaseBookshelfDto {

    @JsonProperty
    private final Long id;

    @JsonProperty
    private final List<BookshelfBookResponseDto> books;

    @JsonProperty
    private final Instant createdAt;

    @JsonProperty
    private final Instant updatedAt;


    public BookshelfResponseDto(String name,
                                BookshelfType type,
                                String description,
                                Long id,
                                List<BookshelfBookResponseDto> books,
                                Instant createdAt,
                                Instant updatedAt) {
        super(name, type, description);
        this.id = id;
        this.books = books;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
