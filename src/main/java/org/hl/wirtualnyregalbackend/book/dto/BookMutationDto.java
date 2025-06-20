package org.hl.wirtualnyregalbackend.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.validation.NotAllFieldsNull;
import org.hl.wirtualnyregalbackend.common.validation.UpdateGroup;

import java.util.List;
import java.util.Locale;

@NotAllFieldsNull(groups = UpdateGroup.class)
@Getter
@Setter
@NoArgsConstructor
public class BookMutationDto extends BaseBookDto {

    private Long formatId;

    @NotEmpty
    private List<Long> genreIds;
}
