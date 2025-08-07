package org.hl.wirtualnyregalbackend.reading_session;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookHelper;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionCreateDto;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionResponseDto;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionUpdateDto;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadPagesEvent;
import org.hl.wirtualnyregalbackend.reading_session.event.ReadTodayEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class ReadingSessionService {

    private final ReadingSessionRepository sessionRepository;
    private final ReadingBookHelper readingBookHelper;
    private final ApplicationEventPublisher eventPublisher;
    private final Clock clock;

    @Transactional
    public ReadingSessionResponseDto createReadingSession(ReadingSessionCreateDto sessionDto) {
        ReadingBook rb = readingBookHelper.findReadingBookEntityId(sessionDto.getReadingBookId());
        ReadingSession session = ReadingSessionMapper.toReadingSession(sessionDto, rb);
        sessionRepository.save(session);

        publishReadTodayEventIfRequired(rb.getBookshelf().getUser());

        Integer readPages = sessionDto.getPageTo() - sessionDto.getPageFrom();
        Integer readMinutes = calculateReadMinutes(sessionDto.getStartedReadingAt(), sessionDto.getFinishedReadingAt());
        eventPublisher.publishEvent(new ReadPagesEvent(readPages, readMinutes, session));

        return ReadingSessionMapper.toReadingSessionResponseDto(session);
    }

    public ReadingSessionResponseDto updateReadingSession(Long sessionId, ReadingSessionUpdateDto sessionDto) {
        ReadingSession session = findReadingSessionEntityById(sessionId);

        Integer pageFrom = sessionDto.getPageFrom();
        Integer currentPageFrom = session.getPageFrom();
        Integer newPageFrom = pageFrom == null
            ? currentPageFrom
            : pageFrom;

        Integer pageTo = sessionDto.getPageTo();
        Integer currentPageTo = session.getPageTo();
        Integer newPageTo = pageTo == null
            ? currentPageTo
            : pageTo;

        session.setPageRange(newPageFrom, newPageTo);

        Instant startedReadingAt = sessionDto.getStartedReadingAt();
        Instant currentStartedReadingAt = session.getStartedReadingAt();
        Instant newStartedReadingAt = startedReadingAt == null
            ? currentStartedReadingAt
            : startedReadingAt;

        Instant finishedReadingAt = sessionDto.getFinishedReadingAt();
        Instant currentFinishedReadingAt = session.getFinishedReadingAt();
        Instant newFinishedReadingAt = finishedReadingAt == null
            ? currentFinishedReadingAt
            : finishedReadingAt;

        session.setReadingPeriod(newStartedReadingAt, newFinishedReadingAt);

        // Check if the reading period or page range has changed and publish event with the difference in read pages and minutes.
        if ((!currentPageFrom.equals(newPageFrom) || !currentPageTo.equals(newPageTo))
            || (!currentStartedReadingAt.equals(newStartedReadingAt) || !currentFinishedReadingAt.equals(newFinishedReadingAt))) {
            Integer readPages = newPageTo - newPageFrom;
            Integer currentReadPages = currentPageTo - currentPageFrom;
            Integer readMinutes = calculateReadMinutes(newStartedReadingAt, newFinishedReadingAt);
            Integer currentReadMinutes = calculateReadMinutes(currentStartedReadingAt, currentFinishedReadingAt);
            eventPublisher.publishEvent(new ReadPagesEvent(readPages - currentReadPages, readMinutes - currentReadMinutes, session));
        }

        return ReadingSessionMapper.toReadingSessionResponseDto(session);
    }


    public List<ReadingSessionResponseDto> findReadingSessions(User user) {
        List<ReadingSession> sessions = sessionRepository.findByUserId(user.getId());
        return sessions
            .stream()
            .map(ReadingSessionMapper::toReadingSessionResponseDto)
            .toList();
    }


    public void deleteReadingSession(Long sessionId) {
        ReadingSession rs = findReadingSessionEntityById(sessionId);
        sessionRepository.delete(rs);
        // TODO delete notes
    }

    private ReadingSession findReadingSessionEntityById(Long id) throws EntityNotFoundException {
        Optional<ReadingSession> sessionOpt = id != null ? sessionRepository.findById(id) : Optional.empty();
        return sessionOpt.orElseThrow(() -> new EntityNotFoundException("ReadingSession with id: %d not found".formatted(id)));
    }


    private void publishReadTodayEventIfRequired(User user) {
        Optional<ReadingSession> lastSessionOpt = sessionRepository.findLastByUserId(user.getId());
        if (!checkIfUserReadToday(lastSessionOpt)) {
            Instant lastReadAt = lastSessionOpt.map(ReadingSession::getStartedReadingAt).orElse(null);
            eventPublisher.publishEvent(new ReadTodayEvent(lastReadAt, user));
        }
    }

    private boolean checkIfUserReadToday(Optional<ReadingSession> lastSessionOpt) {
        if (lastSessionOpt.isPresent()) {
            ZoneId zoneId = clock.getZone();
            LocalDate today = LocalDate.now(zoneId);
            Instant lastReadAt = lastSessionOpt.get().getStartedReadingAt();
            LocalDate lastReadDate = lastReadAt.atZone(zoneId).toLocalDate();
            return today.equals(lastReadDate);
        }
        return false;
    }

    private Integer calculateReadMinutes(Instant startedReadingAt, Instant finishedReadingAt) {
        return (int) Duration.between(startedReadingAt, finishedReadingAt).toMinutes();
    }

}
