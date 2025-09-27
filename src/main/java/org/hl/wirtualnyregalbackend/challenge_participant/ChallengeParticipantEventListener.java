package org.hl.wirtualnyregalbackend.challenge_participant;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.book.BookService;
import org.hl.wirtualnyregalbackend.book.entity.Book;
import org.hl.wirtualnyregalbackend.challenge.entity.Challenge;
import org.hl.wirtualnyregalbackend.challenge.model.ChallengeType;
import org.hl.wirtualnyregalbackend.challenge_participant.entity.ChallengeParticipant;
import org.hl.wirtualnyregalbackend.challenge_participant.model.ChallengeParticipantStatus;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookHelper;
import org.hl.wirtualnyregalbackend.reading_book.event.ReadingBookChangedStatusEvent;
import org.hl.wirtualnyregalbackend.reading_book.model.ReadingStatus;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadPagesEvent;
import org.hl.wirtualnyregalbackend.user.UserService;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@Component
@AllArgsConstructor
@Async
class ChallengeParticipantEventListener {

    private final ChallengeParticipantRepository participantRepository;
    private final BookService bookService;
    private final ReadingBookHelper readingBookHelper;
    private final UserService userService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReadingBookChangedStatusEvent(ReadingBookChangedStatusEvent event) {
        if (!event.status().equals(ReadingStatus.READ)) {
            return;
        }
        Pageable pageable = PageRequest.of(0, 1000);
        Slice<ChallengeParticipant> page;
        Book book = bookService.findBookById(event.bookId());
        User user = userService.findUserById(event.userId());
        do {
            page = participantRepository.findByUserAndStatus(user, ChallengeParticipantStatus.ACTIVE, pageable);
            page.forEach(participant -> {
                Challenge challenge = participant.getChallenge();
                switch (challenge.getType()) {
                    case BOOK_COUNT -> participant.incrementCurrentCount();
                    case GENRE_COUNT ->
                        participant.addCurrentCount(readingBookHelper.countUnreadGenres(book.getGenres(), user));
                    case GENRE_BOOKS -> {
                        if (book.getGenres().contains(challenge.getGenre())) {
                            participant.incrementCurrentCount();
                        }
                    }
                    case AUTHOR_COUNT ->
                        participant.addCurrentCount(readingBookHelper.countUnreadAuthors(book.getAuthors(), user));
                }
            });
            pageable = pageable.next();
        } while (page.hasNext());
    }

    @EventListener
    @Transactional
    public void handleReadPagesEvent(ReadPagesEvent event) {
        User user = userService.findUserById(event.userId());
        Pageable pageable = PageRequest.of(0, 1000);
        Slice<ChallengeParticipant> page;
        do {
            page = participantRepository.findByUserAndStatus(user, ChallengeParticipantStatus.ACTIVE, pageable);
            page.forEach(participant -> {
                Challenge challenge = participant.getChallenge();
                if (challenge.getType().equals(ChallengeType.PAGE_COUNT)) {
                    participant.addCurrentCount(event.readPages());
                }
            });
            pageable = pageable.next();
        } while (page.hasNext());
    }

}
