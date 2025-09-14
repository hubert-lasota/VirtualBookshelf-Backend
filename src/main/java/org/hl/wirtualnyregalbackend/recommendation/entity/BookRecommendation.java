package org.hl.wirtualnyregalbackend.recommendation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;

@Entity
@Table(name = "book_recommendation")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookRecommendation extends BaseRecommendation {

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;


    public BookRecommendation(User user, Book book) {
        super(user, 0F);
        this.book = book;
    }

}
