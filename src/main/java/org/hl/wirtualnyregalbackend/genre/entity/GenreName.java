package org.hl.wirtualnyregalbackend.genre.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedName;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedNamedEntity;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenreName extends BaseEntity implements TranslatedNamedEntity {

    @Embedded
    private TranslatedName translatedName;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;


    public GenreName(TranslatedName translatedName, Genre genre) {
        this.translatedName = translatedName;
        this.genre = genre;
    }

    @Override
    public TranslatedName getTranslatedName() {
        return translatedName;
    }

}
