package org.hl.wirtualnyregalbackend.reading_session;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookQueryService;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_note.ReadingNoteCommandService;
import org.hl.wirtualnyregalbackend.reading_note.entity.ReadingNote;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionRequest;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionResponse;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadPagesEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadTodayEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadingSessionCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadingSessionDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_session.model.SessionReadingDurationRange;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class ReadingSessionCommandService {

    private final ReadingSessionRepository repository;
    private final ReadingSessionQueryService sessionQuery;
    private final ReadingBookQueryService readingBookQuery;
    private final ReadingNoteCommandService noteCommand;
    private final ApplicationEventPublisher eventPublisher;
    private final Clock clock;

    @Transactional
    public ReadingSessionResponse createReadingSession(ReadingSessionRequest sessionRequest) {
        ReadingBook rb = readingBookQuery.findReadingBookById(sessionRequest.readingBookId());
        ReadingSession session = ReadingSessionMapper.toReadingSession(sessionRequest, rb);
        repository.saveAndFlush(session);
        if (sessionRequest.notes() != null) {
            List<ReadingNote> notes = noteCommand.createReadingNotesInSession(session, sessionRequest.notes());
            session.setNotes(notes);
        }

        publishReadTodayEventIfRequired(rb.getBookshelf().getUser(), session.getId());
        eventPublisher.publishEvent(ReadPagesEvent.from(session));
        eventPublisher.publishEvent(ReadingSessionCreatedEvent.from(session));

        log.info("Reading session created: {}", session);
        return ReadingSessionMapper.toReadingSessionResponse(session);
    }

    @Transactional
    public ReadingSessionResponse updateReadingSession(Long sessionId, ReadingSessionRequest sessionRequest) {
        ReadingSession session = sessionQuery.findReadingSessionById(sessionId);
        log.info("Updating reading session: {} by request: {}", session, sessionRequest);

        PageRange oldPr = session.getPageRange();
        PageRange newPr = sessionRequest.pageRange();
        PageRange pr = PageRange.merge(oldPr, newPr);
        session.setPageRange(pr);

        SessionReadingDurationRange oldDr = session.getDurationRange();
        SessionReadingDurationRange newDr = sessionRequest.durationRange();
        SessionReadingDurationRange dr = SessionReadingDurationRange.merge(oldDr, newDr);
        session.setDurationRange(dr);

        ReadPagesEvent rpEvent = ReadPagesEvent.ofDifference(oldPr, newPr, oldDr, newDr, session);
        if (rpEvent != null) {
            eventPublisher.publishEvent(rpEvent);
        }
        log.info("Updated reading session: {}", session);
        return ReadingSessionMapper.toReadingSessionResponse(session);
    }

    @Transactional
    public void deleteReadingSession(Long sessionId) {
        ReadingSession rs = sessionQuery.findReadingSessionById(sessionId);
        eventPublisher.publishEvent(ReadingSessionDeletedEvent.from(rs));
        repository.delete(rs);
        log.info("Deleted reading session: {}", rs);
    }


    private void publishReadTodayEventIfRequired(User user, Long skipSessionId) {
        Optional<ReadingSession> lastSessionOpt = repository.findLastByUserIdAndNotSessionId(user.getId(), skipSessionId);
        if (!checkIfUserReadToday(lastSessionOpt)) {
            Instant lastReadAt = lastSessionOpt
                .map((session) -> session.getDurationRange().getStartedAt())
                .orElse(null);
            eventPublisher.publishEvent(new ReadTodayEvent(lastReadAt, user));
        }
    }

    private boolean checkIfUserReadToday(Optional<ReadingSession> lastSessionOpt) {
        if (lastSessionOpt.isPresent()) {
            ZoneId zoneId = clock.getZone();
            LocalDate today = LocalDate.now(zoneId);
            Instant lastReadAt = lastSessionOpt.get().getDurationRange().getStartedAt();
            LocalDate lastReadDate = lastReadAt.atZone(zoneId).toLocalDate();
            return today.equals(lastReadDate);
        }
        return false;
    }


}
