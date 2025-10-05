package org.hl.wirtualnyregalbackend.genre.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.translation.TranslatedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Entity
@Table(name = "genre")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
@Getter
public class Genre extends BaseEntity {

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<GenreTranslation> translations = new ArrayList<>();

    @Column(name = "total_books")
    private int totalBooks;

    public Genre(TranslatedName translatedName) {
        GenreTranslation genreTranslation = new GenreTranslation(translatedName, this);
        this.translations.add(genreTranslation);
        this.totalBooks = 0;
    }


    public void incrementTotalBooks() {
        this.totalBooks++;
    }

    public List<GenreTranslation> getTranslations() {
        return Collections.unmodifiableList(translations);
    }

}
