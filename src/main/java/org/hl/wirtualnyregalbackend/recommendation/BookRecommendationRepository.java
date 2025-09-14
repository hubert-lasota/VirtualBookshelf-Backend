package org.hl.wirtualnyregalbackend.recommendation;

import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.recommendation.entity.BookRecommendation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface BookRecommendationRepository extends JpaRepository<BookRecommendation, Long> {

    @Query(value = """
        SELECT DISTINCT b.*, 
               (COALESCE(book_score.total_score, 0) + 
                COALESCE(author_score.total_score, 0) * 0.8 + 
                COALESCE(genre_score.total_score, 0) * 0.6 +
                COALESCE(preference_score.bonus, 0)) as final_score
        FROM book b
        LEFT JOIN (
            SELECT br.book_id, SUM(br.score) as total_score 
            FROM book_recommendation br 
            WHERE br.user_id = :userId 
            GROUP BY br.book_id
        ) book_score ON b.id = book_score.book_id
        LEFT JOIN (
            SELECT ba.book_id, SUM(ar.score) as total_score
            FROM book_author ba
            JOIN author_recommendation ar ON ba.author_id = ar.author_id
            WHERE ar.user_id = :userId
            GROUP BY ba.book_id
        ) author_score ON b.id = author_score.book_id
        LEFT JOIN (
            SELECT bg.book_id, SUM(gr.score) as total_score
            FROM book_genre bg
            JOIN genre_recommendation gr ON bg.genre_id = gr.genre_id
            WHERE gr.user_id = :userId
            GROUP BY bg.book_id
        ) genre_score ON b.id = genre_score.book_id
        LEFT JOIN (
            SELECT bg2.book_id, COUNT(*) * 0.2 as bonus
            FROM book_genre bg2
            JOIN user_genre_preferences ugp ON bg2.genre_id = ugp.genre_id
            WHERE ugp.user_id = :userId
            GROUP BY bg2.book_id
        ) preference_score ON b.id = preference_score.book_id
        WHERE (book_score.total_score IS NOT NULL 
               OR author_score.total_score IS NOT NULL 
               OR genre_score.total_score IS NOT NULL
               OR preference_score.bonus IS NOT NULL)
        ORDER BY final_score DESC
        """,
        countQuery = """
            SELECT COUNT(DISTINCT b.id)
            FROM book b
            LEFT JOIN book_recommendation br ON b.id = br.book_id AND br.user_id = :userId
            LEFT JOIN book_author ba ON b.id = ba.book_id
            LEFT JOIN author_recommendation ar ON ba.author_id = ar.author_id AND ar.user_id = :userId
            LEFT JOIN book_genre bg ON b.id = bg.book_id
            LEFT JOIN genre_recommendation gr ON bg.genre_id = gr.genre_id AND gr.user_id = :userId
            LEFT JOIN user_genre_preferences ugp ON bg.genre_id = ugp.genre_id AND ugp.user_id = :userId
            WHERE (br.score IS NOT NULL 
                   OR ar.score IS NOT NULL 
                   OR gr.score IS NOT NULL
                   OR ugp.genre_id IS NOT NULL)
            """,
        nativeQuery = true)
    Page<Book> findRecommendedBooksForUser(@Param("userId") Long userId, Pageable pageable);

    Optional<BookRecommendation> findByBookAndUser(Book book, User user);

}
