package org.hl.wirtualnyregalbackend.book_series.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedName;

import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookSeries extends BaseEntity {

    @OneToMany
    private List<BookSeriesName> names;

    public BookSeries(TranslatedName translatedName) {
        names = List.of(new BookSeriesName(translatedName));
    }

    public List<BookSeriesName> getNames() {
        return Collections.unmodifiableList(names);
    }

}
