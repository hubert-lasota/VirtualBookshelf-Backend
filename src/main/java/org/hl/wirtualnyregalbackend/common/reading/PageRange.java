package org.hl.wirtualnyregalbackend.common.reading;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.validation.CreateGroup;
import org.springframework.lang.Nullable;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class PageRange {

    @Min(1)
    @NotNull(groups = CreateGroup.class)
    @Column(name = "page_from")
    private Integer from;

    @Min(1)
    @NotNull(groups = CreateGroup.class)
    @Column(name = "page_to")
    private Integer to;

    public static PageRange merge(PageRange oldPr, @Nullable PageRange newPr) {
        if (newPr == null) {
            return oldPr;
        }

        Integer from = newPr.from != null ? newPr.from : oldPr.from;
        Integer to = newPr.to != null ? newPr.to : oldPr.to;
        PageRange pr = new PageRange(from, to);
        if (!isValid(from, to)) {
            throw new InvalidPageRangeException(pr);
        }
        return pr;
    }

    private static boolean isValid(Integer from, Integer to) {
        return from <= to;
    }

    public Integer getReadPages() {
        return to - from;
    }

    @AssertTrue(groups = CreateGroup.class, message = "to must be greater or equal from")
    @JsonIgnore
    public boolean validate() {
        return isValid(from, to);
    }

}
