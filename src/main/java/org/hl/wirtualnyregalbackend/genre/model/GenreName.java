package org.hl.wirtualnyregalbackend.genre.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.localization.LocalizedName;
import org.hl.wirtualnyregalbackend.common.localization.LocalizedNamedEntity;

@Entity
public class GenreName extends BaseEntity implements LocalizedNamedEntity {

    @Embedded
    private LocalizedName localizedName;

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    protected GenreName() { }

    public GenreName(LocalizedName localizedName, Genre genre) {
        this.localizedName = localizedName;
    }

    @Override
    public LocalizedName getLocalizedName() {
        return localizedName;
    }

}
