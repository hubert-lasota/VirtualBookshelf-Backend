package org.hl.wirtualnyregalbackend.application.book;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book_genre")
public class BookGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "bookGenres")
    private Set<Book> books = new HashSet<>();

    protected BookGenre() { }

    public BookGenre(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "BookGenreEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
