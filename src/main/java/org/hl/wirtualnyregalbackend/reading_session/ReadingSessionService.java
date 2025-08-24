package org.hl.wirtualnyregalbackend.reading_session;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.model.PageRange;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookHelper;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionCreateRequest;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionPageResponse;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionResponse;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionUpdateRequest;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadPagesEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadTodayEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadingSessionCreatedEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadingSessionDeletedEvent;
import org.hl.wirtualnyregalbackend.reading_session.exception.ReadingSessionNotFoundException;
import org.hl.wirtualnyregalbackend.reading_session.model.SessionReadingDurationRange;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

@Service
@AllArgsConstructor
class ReadingSessionService {

    private final ReadingSessionRepository sessionRepository;
    private final ReadingBookHelper readingBookHelper;
    private final ApplicationEventPublisher eventPublisher;
    private final Clock clock;

    @Transactional
    public ReadingSessionResponse createReadingSession(ReadingSessionCreateRequest sessionRequest) {
        ReadingBook rb = readingBookHelper.findReadingBookById(sessionRequest.getReadingBookId());
        ReadingSession session = ReadingSessionMapper.toReadingSession(sessionRequest, rb);
        sessionRepository.save(session);

        publishReadTodayEventIfRequired(rb.getBookshelf().getUser());

        eventPublisher.publishEvent(
            new ReadPagesEvent(session.getPageRange().getReadPages(), session.getDurationRange().getReadMinutes(), session)
        );
        eventPublisher.publishEvent(new ReadingSessionCreatedEvent(session));
        return ReadingSessionMapper.toReadingSessionResponse(session);
    }

    public ReadingSessionResponse updateReadingSession(Long sessionId, ReadingSessionUpdateRequest sessionRequest) {
        ReadingSession session = findReadingSessionById(sessionId);

        String description = sessionRequest.getDescription();
        if (description != null) {
            session.setDescription(description);
        }

        PageRange oldPr = session.getPageRange();
        PageRange newPr = sessionRequest.getPageRange();
        PageRange pr = PageRange.merge(oldPr, newPr);
        session.setPageRange(pr);

        SessionReadingDurationRange oldRr = session.getDurationRange();
        SessionReadingDurationRange newRr = sessionRequest.getDurationRange();
        SessionReadingDurationRange rr = SessionReadingDurationRange.merge(oldRr, newRr);
        session.setDurationRange(rr);

        // Check if the reading period or page range has changed and publish event with the difference in read pages and minutes.
        if ((!oldPr.equals(pr)) || (!oldRr.equals(rr))) {
            Integer readPages = pr.getReadPages() - oldPr.getReadPages();
            Integer readMinutes = rr.getReadMinutes() - oldRr.getReadMinutes();
            eventPublisher.publishEvent(new ReadPagesEvent(readPages, readMinutes, session));
        }

        return ReadingSessionMapper.toReadingSessionResponse(session);
    }


    public ReadingSessionPageResponse findReadingSessions(User user, Pageable pageable) {
        Page<ReadingSessionResponse> page = sessionRepository
            .findByUserId(user.getId(), pageable)
            .map(ReadingSessionMapper::toReadingSessionResponse);
        return ReadingSessionPageResponse.from(page);
    }


    public void deleteReadingSession(Long sessionId) {
        ReadingSession rs = findReadingSessionById(sessionId);
        eventPublisher.publishEvent(new ReadingSessionDeletedEvent(rs));
        sessionRepository.delete(rs);
    }

    private ReadingSession findReadingSessionById(Long id) throws ReadingSessionNotFoundException {
        Optional<ReadingSession> sessionOpt = id != null ? sessionRepository.findById(id) : Optional.empty();
        return sessionOpt.orElseThrow(() -> new ReadingSessionNotFoundException(id));
    }


    private void publishReadTodayEventIfRequired(User user) {
        Optional<ReadingSession> lastSessionOpt = sessionRepository.findLastByUserId(user.getId());
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
