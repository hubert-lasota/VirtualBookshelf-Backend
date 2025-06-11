package org.hl.wirtualnyregalbackend.book_format.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "book_format")
public class BookFormat extends BaseEntity {

    @OneToMany
    private List<BookFormatTranslation> translations;


    public List<BookFormatTranslation> getTranslations() {
        return Collections.unmodifiableList(translations);
    }

}
