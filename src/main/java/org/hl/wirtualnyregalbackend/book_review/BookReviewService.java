package org.hl.wirtualnyregalbackend.book_review;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.book.BookHelper;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.book_review.dto.BookReviewCreateDto;
import org.hl.wirtualnyregalbackend.book_review.entity.BookReview;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.common.model.PageResponseDto;
import org.hl.wirtualnyregalbackend.common.review.*;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.hl.wirtualnyregalbackend.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final BookHelper bookHelper;
    private final UserService userService;

    public ReviewResponseDto createBookReview(BookReviewCreateDto reviewDto, User user) {
        Book book = bookHelper.findBookById(reviewDto.getBookId());
        BookReview bookReview = BookReviewMapper.toBookReview(reviewDto, book, user);
        bookReviewRepository.save(bookReview);
        return ReviewMapper.toReviewResponseDto(bookReview);
    }

    public ReviewResponseDto updateBookReview(Long bookReviewId, ReviewDto reviewDto) {
        BookReview bookReview = findBookReviewEntityById(bookReviewId);
        Float rating = reviewDto.getRating();
        if (rating != null) {
            bookReview.setRating(rating);
        }
        String content = reviewDto.getContent();
        if (content != null) {
            bookReview.setContent(content);
        }
        bookReviewRepository.save(bookReview);
        return ReviewMapper.toReviewResponseDto(bookReview);
    }

    public void deleteBookReview(Long bookReviewId) {
        BookReview bookReview = findBookReviewEntityById(bookReviewId);
        bookReviewRepository.delete(bookReview);
    }

    public ReviewStats getBookReviewStats(Long bookId) {
        List<ReviewStats> stats = getBookReviewStatsByBookIds(List.of(bookId));
        if (stats.isEmpty()) {
            return null;
        }
        return stats.get(0);
    }

    public List<ReviewStats> getBookReviewStatsByBookIds(List<Long> bookIds) {
        List<ReviewStats> raws = bookReviewRepository.getReviewStatsByBookIds(bookIds);
        return ReviewUtils.roundAverage(raws);
    }

    public PageResponseDto<ReviewResponseDto> findBookReviews(Long bookId, Pageable pageable) {
        Page<BookReview> page = bookReviewRepository.findByBookId(bookId, pageable);
        Page<ReviewResponseDto> response = page.map(ReviewMapper::toReviewResponseDto);
        return new PageResponseDto<>(response, "reviews");
    }

    public boolean isAuthor(Long bookRatingId, Long userId) {
        return bookReviewRepository.isAuthor(bookRatingId, userId);
    }

    public BookReview findBookReviewEntityById(Long id) throws EntityNotFoundException {
        Optional<BookReview> reviewOpt = id != null ? bookReviewRepository.findById(id) : Optional.empty();
        return reviewOpt.orElseThrow(() -> new EntityNotFoundException("BookReview with id = '%d' not found.".formatted(id)));
    }

}
