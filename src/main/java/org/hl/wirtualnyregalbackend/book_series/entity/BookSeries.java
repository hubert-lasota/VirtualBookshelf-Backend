package org.hl.wirtualnyregalbackend.book_series.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedName;

import java.util.List;

@Entity
public class BookSeries extends BaseEntity {

    @OneToMany
    private List<BookSeriesName> names;

    protected BookSeries() {
    }

    public BookSeries(TranslatedName translatedName) {
        names = List.of(new BookSeriesName(translatedName));
    }

    public List<BookSeriesName> getNames() {
        return names;
    }

}
