package org.hl.wirtualnyregalbackend.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.common.error.exception.InvalidPageRangeException;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.springframework.lang.Nullable;

@Embeddable
public record PageRange(
    @Min(1)
    @NotNull(groups = CreateGroup.class)
    @Column(name = "page_from")
    Integer from,
    @Min(1)
    @NotNull(groups = CreateGroup.class)
    @Column(name = "page_to")
    Integer to
) {

    public static PageRange merge(PageRange oldPr, @Nullable PageRange newPr) {
        if (newPr == null) {
            return oldPr;
        }

        Integer from = newPr.from() != null ? newPr.from() : oldPr.from();
        Integer to = newPr.to() != null ? newPr.to() : oldPr.to();
        PageRange pr = new PageRange(from, to);
        if (!isValid(from, to)) {
            throw new InvalidPageRangeException(pr);
        }
        return pr;
    }

    private static boolean isValid(Integer from, Integer to) {
        return from <= to;
    }

    @JsonIgnore
    public Integer getReadPages() {
        return to - from;
    }

    @JsonIgnore
    public void validate(Book book) {
        if (to > book.getPageCount()) {
            throw new InvalidPageRangeException(this, book);
        }
    }

    @AssertTrue(groups = CreateGroup.class, message = "to must be greater or equal from")
    @JsonIgnore
    public boolean validate() {
        return isValid(from, to);
    }

}
