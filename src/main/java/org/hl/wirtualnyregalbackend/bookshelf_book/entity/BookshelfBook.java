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

import java.time.Instant;
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
    private Float progressPercentage;

    @Column
    @Enumerated(EnumType.STRING)
    private BookReadingStatus status;

    @Column
    private Instant startedReadingAt;

    @Column
    private Instant finishedReadingAt;

    @ManyToOne
    @JoinColumn(name = "bookshelf_id")
    private Bookshelf bookshelf;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    public BookshelfBook(Integer currentPage,
                         Book book,
                         Instant startedReadingAt,
                         Instant finishedReadingAt,
                         BookReadingStatus status) {
        this.book = Objects.requireNonNull(book, "book cannot be null.");
        this.startedReadingAt = startedReadingAt;
        this.finishedReadingAt = finishedReadingAt;
        this.status = status;
        setCurrentPage(currentPage);
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

    private void validateCurrentPage(Integer currentPage) {
        int bookPageCount = book.getPageCount();
        if (currentPage > bookPageCount) {
            throw new InvalidRequestException("Current page is higher than book number of pages");
        }
    }

    private Float calculateProgressPercentage(Integer currentPage) {
        int bookPageCount = book.getPageCount();
        if (currentPage == null || currentPage <= 0) {
            return 0F;
        }

        if (currentPage >= bookPageCount) {
            return 100F;
        }

        return ((float) currentPage) / bookPageCount * 100F;
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
