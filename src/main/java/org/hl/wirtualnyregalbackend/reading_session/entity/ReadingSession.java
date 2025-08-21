package org.hl.wirtualnyregalbackend.reading_session.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.common.model.ReadingDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;

@Entity
@Table(name = "reading_session")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReadingSession extends BaseEntity {

    @Embedded
    private PageRange pageRange;

    @Embedded
    private ReadingDurationRange durationRange;

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "reading_book_id")
    @Setter(AccessLevel.NONE)
    private ReadingBook readingBook;

}
