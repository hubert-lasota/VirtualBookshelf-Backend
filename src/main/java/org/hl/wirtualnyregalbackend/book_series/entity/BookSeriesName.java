package org.hl.wirtualnyregalbackend.book_series.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedName;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedNamedEntity;


@Entity
public class BookSeriesName extends BaseEntity implements TranslatedNamedEntity {

    @Embedded
    private TranslatedName translatedName;

    @ManyToOne
    @JoinColumn(name = "book_series_id")
    private BookSeries series;

    protected BookSeriesName() {
    }

    public BookSeriesName(TranslatedName translatedName) {
        this.translatedName = translatedName;
    }


    @Override
    public TranslatedName getTranslatedName() {
        return translatedName;
    }

}
