package org.hl.wirtualnyregalbackend.book_format.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "book_format")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public class BookFormat extends BaseEntity {

    @OneToMany(mappedBy = "format", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookFormatTranslation> translations;


    public List<BookFormatTranslation> getTranslations() {
        return Collections.unmodifiableList(translations);
    }

}
