package org.hl.wirtualnyregalbackend.reading_session.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.reading.PageRange;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;
import org.hl.wirtualnyregalbackend.reading_session.model.SessionReadingDurationRange;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "reading_session")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReadingSession extends BaseEntity {

    @Column
    private String title;

    @Embedded
    private PageRange pageRange;

    @Embedded
    private SessionReadingDurationRange durationRange;

    @ManyToOne
    @JoinColumn(name = "reading_book_id")
    @Setter(AccessLevel.NONE)
    private ReadingBook readingBook;

    @OneToMany(mappedBy = "readingSession")
    private List<ReadingNote> notes;


    public List<ReadingNote> getNotes() {
        return Collections.unmodifiableList(notes);
    }

}
