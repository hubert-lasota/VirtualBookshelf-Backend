package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.URL;
import org.hl.wirtualnyregalbackend.common.json.LocaleDeserializer;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.StringConstraints;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;

import java.util.List;
import java.util.Locale;

@NotAllFieldsNull(groups = UpdateGroup.class)
@Getter
@Setter
@NoArgsConstructor
public class BookMutationDto {

    @Min(0)
    protected Integer publicationYear;
    private Long formatId;
    @NotEmpty
    private List<Long> genreIds;
    @NotNull(groups = CreateGroup.class)
    @ISBN
    private String isbn;
    @NotNull(groups = CreateGroup.class)
    @StringConstraints
    private String title;
    @NotNull(groups = CreateGroup.class)
    @Min(0)
    private Integer pageCount;

    @JsonDeserialize(using = LocaleDeserializer.class)
    @JsonProperty("languageCode")
    private Locale language;

    @StringConstraints(allowMultipleSpacesBetweenWords = true)
    private String description;

    @URL
    private String coverUrl;

    @Valid
    private PublisherWithIdDto publisher;

    @NotEmpty(groups = CreateGroup.class)
    @Valid
    private List<AuthorWithIdDto> authors;

    public void setIsbn(String isbn) {
        this.isbn = isbn != null ? isbn.replaceAll("-", "") : null;
    }

}
