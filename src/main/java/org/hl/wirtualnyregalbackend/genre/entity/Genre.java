package org.hl.wirtualnyregalbackend.genre.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "genre")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Genre extends BaseEntity {

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GenreTranslation> translations = new ArrayList<>();

    public Genre(TranslatedName translatedName) {
        GenreTranslation genreTranslation = new GenreTranslation(translatedName, this);
        this.translations.add(genreTranslation);
    }


    public List<GenreTranslation> getTranslations() {
        return Collections.unmodifiableList(translations);
    }

}
