package org.hl.wirtualnyregalbackend.bookshelf_book.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.bookshelf.entity.Bookshelf;
import org.hl.wirtualnyregalbackend.common.exception.InvalidRequestException;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;
import org.hl.wirtualnyregalbackend.common.model.RangeDate;

import java.util.Objects;

@Entity
@Table(name = "bookshelf_book")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookshelfBook extends BaseEntity {

    @Column(name = "current_page")
    private Integer currentPage;

    @Column(name = "progress_percentage")
    @Setter(AccessLevel.NONE)
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


    public BookshelfBook(Integer currentPage,
                         Book book,
                         RangeDate rangeDate,
                         BookReadingStatus status) {
        this.book = Objects.requireNonNull(book, "book cannot be null.");
        this.rangeDate = rangeDate;
        this.currentPage = validateCurrentPage(currentPage);
        this.progressPercentage = calculateProgressPercentage(currentPage);
        this.status = status;
    }


    public void read() {
        this.status = BookReadingStatus.READ;
    }

    public void reading() {
        this.status = BookReadingStatus.READING;
    }

    public void setCurrentPage(Integer currentPage) {
        validateCurrentPage(currentPage);
        this.currentPage = currentPage;
        this.progressPercentage = calculateProgressPercentage(currentPage);
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
