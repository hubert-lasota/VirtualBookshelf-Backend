package org.hl.wirtualnyregalbackend.genre.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.localization.LocalizedName;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "genre")
public class Genre extends BaseEntity {

    @OneToMany
    private List<GenreName> names = new ArrayList<>();

    protected Genre() {
    }

    public Genre(LocalizedName localizedName) {
        GenreName genreName = new GenreName(localizedName, this);
        this.names.add(genreName);
    }


    public List<GenreName> getNames() {
        return names;
    }

}
