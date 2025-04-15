package org.hl.wirtualnyregalbackend.book.model.entity;

import jakarta.persistence.*;
import org.hl.wirtualnyregalbackend.common.jpa.BaseEntity;

import java.util.List;
import java.util.Set;

@Entity
public class BookSeries extends BaseEntity {

    @OneToMany
    private List<BookSeriesName> names;

    @ManyToMany()
    @JoinTable(name="book_book_series",
            joinColumns = @JoinColumn(name = "book_series_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books;


    public List<BookSeriesName> getNames() {
        return names;
    }

}
