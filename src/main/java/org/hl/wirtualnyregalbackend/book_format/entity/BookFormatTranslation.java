package org.hl.wirtualnyregalbackend.book_format.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedName;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedNamedEntity;

@Entity
@Table(name = "book_format_translation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookFormatTranslation extends BaseEntity implements TranslatedNamedEntity {

    @Embedded
    private TranslatedName translatedName;

    @ManyToOne
    @JoinColumn(name = "book_format_id")
    private BookFormat format;


    @Override
    public TranslatedName getTranslatedName() {
        return translatedName;
    }

}
