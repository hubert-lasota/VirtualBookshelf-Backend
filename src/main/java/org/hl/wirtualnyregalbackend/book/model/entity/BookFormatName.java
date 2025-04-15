package org.hl.wirtualnyregalbackend.book.model.entity;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.localization.LocalizedName;
import org.hl.wirtualnyregalbackend.common.localization.LocalizedNamedEntity;

@Entity
@Table(name = "book_format_name")
public class BookFormatName extends BaseEntity implements LocalizedNamedEntity {

    @Embedded
    private LocalizedName localizedName;

    @ManyToOne
    @JoinColumn(name = "book_format_id")
    private BookFormat format;


    protected BookFormatName() { }

    public BookFormatName(LocalizedName localizedName, BookFormat format) {
        this.localizedName = localizedName;
        this.format = format;
    }

    public void updateName(String name) {
        if(name != null) {
            this.localizedName = new LocalizedName(name, localizedName.getLanguage());
        }
    }

    @Override
    public LocalizedName getLocalizedName() {
        return localizedName;
    }

}
