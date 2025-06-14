package org.hl.wirtualnyregalbackend.book_format.entity;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedName;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedNamedEntity;

@Entity
@Table(name = "book_format_translation")
public class BookFormatTranslation extends BaseEntity implements TranslatedNamedEntity {

    @Embedded
    private TranslatedName translatedName;

    @ManyToOne
    @JoinColumn(name = "book_format_id")
    private BookFormat format;


    protected BookFormatTranslation() {
    }

    public BookFormatTranslation(TranslatedName translatedName, BookFormat format) {
        this.translatedName = translatedName;
        this.format = format;
    }


    @Override
    public TranslatedName getTranslatedName() {
        return translatedName;
    }

}
