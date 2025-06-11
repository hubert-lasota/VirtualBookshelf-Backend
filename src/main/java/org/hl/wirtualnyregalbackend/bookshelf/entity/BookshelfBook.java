package org.hl.wirtualnyregalbackend.bookshelf.entity;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bookshelf_book")
public class BookshelfBook extends BaseEntity {

    @Column(name = "current_page")
    private Integer currentPage;

    @Column(name = "progress_percentage")
    private Integer progressPercentage;

    @Column
    @Enumerated(EnumType.STRING)
    private BookReadingStatus status;

    @Embedded
    private RangeDate rangeDate;

    @ManyToOne
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "bookshelfBook")
    private List<BookshelfBookNote> notes = new ArrayList<>();


    protected BookshelfBook() {
    }

    public BookshelfBook(Integer currentPage,
                         Book book,
                         RangeDate rangeDate,
                         BookReadingStatus status,
                         List<BookshelfBookNote> notes) {
        this.book = Objects.requireNonNull(book, "book cannot be null.");
        this.rangeDate = rangeDate;
        this.currentPage = validateCurrentPage(currentPage);
        this.progressPercentage = calculateProgressPercentage(currentPage);
        this.status = status;
        notes.forEach(note -> note.setBookshelfBook(this));
        this.notes = notes;
    }

    public Integer getProgressPercentage() {
        return progressPercentage;
    }


    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        validateCurrentPage(currentPage);
        this.currentPage = currentPage;
        this.progressPercentage = calculateProgressPercentage(currentPage);
    }

    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public BookReadingStatus getStatus() {
        return status;
    }

    public void setStatus(BookReadingStatus status) {
        this.status = status;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public RangeDate getRangeDate() {
        return rangeDate;
    }

    public void setRangeDate(RangeDate rangeDate) {
        this.rangeDate = rangeDate;
    }

    public List<BookshelfBookNote> getNotes() {
        return Collections.unmodifiableList(notes);
    }

    public void setNotes(List<BookshelfBookNote> notes) {
        this.notes = notes;
    }

    private Integer validateCurrentPage(Integer currentPage) {
        int bookPageCount = book.getPageCount();
        if (currentPage > bookPageCount) {
            throw new InvalidRequestException("Current page is higher than book number of pages");
        }
        return currentPage;
    }

    private Integer calculateProgressPercentage(Integer currentPage) {
        int bookPageCount = book.getPageCount();
        float progress = (float) currentPage / (float) bookPageCount;
        return (int) progress * 100;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookshelfBook that = (BookshelfBook) o;
        if (this.id != null && that.id != null) {
            return this.id.equals(that.id);
        }
        return Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(book);
    }

}
