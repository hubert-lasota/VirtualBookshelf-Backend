package org.hl.wirtualnyregalbackend.common.translation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Locale;

@Embeddable
public class TranslatedName {

    @Column
    private String name;

    @Column(name = "language_tag")
    private Locale language;

    protected TranslatedName() {
    }

    public TranslatedName(String name, Locale language) {
        this.name = name;
        this.language = language;
    }

    public String getName() {
        return name;
    }


    public Locale getLanguage() {
        return language;
    }

}
