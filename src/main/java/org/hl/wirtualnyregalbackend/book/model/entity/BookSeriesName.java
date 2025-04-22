package org.hl.wirtualnyregalbackend.book.model.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.localization.LocalizedName;
import org.hl.wirtualnyregalbackend.common.localization.LocalizedNamedEntity;


@Entity
public class BookSeriesName extends BaseEntity implements LocalizedNamedEntity {

    @Embedded
    private LocalizedName localizedName;

    @ManyToOne
    @JoinColumn(name = "book_series_id")
    private BookSeries series;

    protected BookSeriesName() {
    }

    public BookSeriesName(LocalizedName localizedName) {
        this.localizedName = localizedName;
    }


    @Override
    public LocalizedName getLocalizedName() {
        return localizedName;
    }

}
