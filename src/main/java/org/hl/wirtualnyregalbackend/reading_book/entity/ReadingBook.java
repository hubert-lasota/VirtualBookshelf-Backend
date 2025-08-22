package org.hl.wirtualnyregalbackend.reading_book.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingBookDurationRange;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;

@Entity
@Table(name = "reading_book")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReadingBook extends BaseEntity {

    @Column
    @Enumerated(EnumType.STRING)
    private ReadingStatus status;

    @Embedded
    private ReadingBookDurationRange durationRange;

    @ManyToOne
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;

    @ManyToOne
    @JoinColumn(name = "book_id")
    @Setter(AccessLevel.NONE)
    private Book book;
// TODO dodac totalNotes
}
