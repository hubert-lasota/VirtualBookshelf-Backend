package org.hl.wirtualnyregalbackend.bookshelf.model;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

@Entity
@Table(name = "bookshelf_book_note")
public class BookshelfBookNote extends BaseEntity {

    @Column
    private String content;

    @Column(name = "start_page")
    private Integer startPage;

    @Column(name = "end_page")
    private Integer endPage;

    @ManyToOne
    @JoinColumn(name = "bookshelf_book_id")
    private BookshelfBook bookshelfBook;

    protected BookshelfBookNote() {
    }


    public BookshelfBookNote(String content,
                             Integer startPage,
                             Integer endPage) {
        this.content = content;
        this.startPage = startPage;
        this.endPage = endPage;
    }

    void setBookshelfBook(BookshelfBook bookshelfBook) {
        this.bookshelfBook = bookshelfBook;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }


}
