package org.hl.wirtualnyregalbackend.common.localization;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Locale;

@Embeddable
public class LocalizedName {

    @Column
    private String name;

    @Column(name = "language_tag")
    private Locale language;

    protected LocalizedName() { }

    public LocalizedName(String name, Locale language) {
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
