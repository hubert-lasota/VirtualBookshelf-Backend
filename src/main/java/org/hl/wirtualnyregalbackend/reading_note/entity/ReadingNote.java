package org.hl.wirtualnyregalbackend.reading_note.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;

@Entity
@Table(name = "reading_note")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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


    public ReadingNote(String title, String content, PageRange pageRange, ReadingBook readingBook) {
        this.title = title;
        this.content = content;
        this.readingBook = readingBook;
        setPageRange(pageRange);
    }

    public void setPageRange(PageRange pageRange) {
        pageRange.validate(readingBook.getBook());
        this.pageRange = pageRange;
    }

}
