package org.hl.wirtualnyregalbackend.genre.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedName;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedNamedEntity;

@Entity
public class GenreName extends BaseEntity implements TranslatedNamedEntity {

    @Embedded
    private TranslatedName translatedName;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    protected GenreName() {
    }

    public GenreName(TranslatedName translatedName, Genre genre) {
        this.translatedName = translatedName;
    }

    @Override
    public TranslatedName getTranslatedName() {
        return translatedName;
    }

}
