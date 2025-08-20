package org.hl.wirtualnyregalbackend.reading_note.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;

@Entity
@Table(name = "reading_note")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReadingNote extends BaseEntity {

    @Column
    private String title;

    @Column
    private String content;

    @Embedded
    private PageRange pageRange;

    @ManyToOne
    @JoinColumn(name = "reading_book_id")
    private ReadingBook readingBook;

}
