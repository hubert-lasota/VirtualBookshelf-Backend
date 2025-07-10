package org.hl.wirtualnyregalbackend.reading_session;

import lombok.AllArgsConstructor;
import org.hl.wirtualnyregalbackend.common.exception.EntityNotFoundException;
import org.hl.wirtualnyregalbackend.reading_book.ReadingBookHelper;
import org.hl.wirtualnyregalbackend.reading_book.entity.ReadingBook;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionCreateDto;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionMutationDto;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionResponseDto;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.hl.wirtualnyregalbackend.security.entity.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
class ReadingSessionService {

    private final ReadingSessionRepository sessionRepository;
    private final ReadingBookHelper readingBookHelper;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ReadingSessionResponseDto createReadingSession(ReadingSessionCreateDto sessionDto) {
        ReadingBook rb = readingBookHelper.findReadingBookEntityId(sessionDto.getReadingBookId());
        ReadingSession session = ReadingSessionMapper.toReadingSession(sessionDto, rb);
        // TODO process in event
//        Book book = rb.getBook();
//        if (book.getPageCount().equals(session.getPageTo())) {
//            rb.setStatus(ReadingStatus.READ);
//        }

        sessionRepository.save(session);
        return ReadingSessionMapper.toReadingSessionResponseDto(session);
    }

    public ReadingSessionResponseDto updateReadingSession(Long sessionId, ReadingSessionMutationDto sessionDto) {
        ReadingSession session = findReadingSessionEntityById(sessionId);

        Integer dtoPageFrom = sessionDto.getPageFrom();
        Integer pageFrom = dtoPageFrom == null
            ? session.getPageFrom()
            : dtoPageFrom;

        Integer dtoPageTo = sessionDto.getPageTo();
        Integer pageTo = dtoPageTo == null
            ? session.getPageTo()
            : dtoPageTo;

        session.setPageRange(pageFrom, pageTo);

        Instant dtoStartedReadingAt = sessionDto.getStartedReadingAt();
        Instant startedReadingAt = dtoStartedReadingAt == null
            ? session.getStartedReadingAt()
            : dtoStartedReadingAt;

        Instant dtoFinishedReadingAt = sessionDto.getFinishedReadingAt();
        Instant finishedReadingAt = dtoFinishedReadingAt == null
            ? session.getFinishedReadingAt()
            : dtoFinishedReadingAt;

        session.setReadingPeriod(startedReadingAt, finishedReadingAt);

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

}
