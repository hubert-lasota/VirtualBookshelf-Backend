package org.hl.wirtualnyregalbackend.reading_note.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
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

    @Column(name = "page_from")
    @Setter(AccessLevel.NONE)
    private Integer pageFrom;

    @Column(name = "page_to")
    @Setter(AccessLevel.NONE)
    private Integer pageTo;

    @ManyToOne
    @JoinColumn(name = "reading_book_id")
    private ReadingBook readingBook;

    public ReadingNote(String title,
                       String content,
                       Integer pageFrom,
                       Integer pageTo,
                       ReadingBook readingBook) {
        this.title = title;
        this.content = content;
        this.readingBook = readingBook;
        setPageRange(pageFrom, pageTo);
    }

    public void setPageRange(Integer pageFrom, Integer pageTo) {
        if (pageFrom > pageTo) {
            throw new InvalidRequestException("Page from cannot be greater than page to");
        }
        this.pageFrom = pageFrom;
        this.pageTo = pageTo;
    }

}
