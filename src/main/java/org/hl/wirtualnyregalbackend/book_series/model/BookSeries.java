package org.hl.wirtualnyregalbackend.book_series.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.localization.LocalizedName;

import java.util.List;

@Entity
public class BookSeries extends BaseEntity {

    @OneToMany
    private List<BookSeriesName> names;

    protected BookSeries() {
    }

    public BookSeries(LocalizedName localizedName) {
        names = List.of(new BookSeriesName(localizedName));
    }

    public List<BookSeriesName> getNames() {
        return names;
    }

}
