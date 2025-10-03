package org.hl.wirtualnyregalbackend.genre.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedName;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedNamedEntity;

@Entity
@Table(name = "genre_translation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenreTranslation extends BaseEntity implements TranslatedNamedEntity {

    @Embedded
    private TranslatedName translatedName;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;


    public GenreTranslation(TranslatedName translatedName, Genre genre) {
        this.translatedName = translatedName;
        this.genre = genre;
    }


    @Override
    public TranslatedName getTranslatedName() {
        return translatedName;
    }


    @Override
    public String toString() {
        return "GenreTranslation{" +
            "id=" + getId() +
            "translatedName=" + translatedName +
            ", genreId=" + genre.getId() +
            '}';
    }

}
