package org.hl.wirtualnyregalbackend.reading_session;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hl.wirtualnyregalbackend.auth.entity.User;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionListResponse;
import org.hl.wirtualnyregalbackend.reading_session.dto.ReadingSessionResponse;
import org.hl.wirtualnyregalbackend.reading_session.entity.ReadingSession;
import org.hl.wirtualnyregalbackend.reading_session.exception.ReadingSessionNotFoundException;
import org.hl.wirtualnyregalbackend.reading_session.model.ReadingSessionFilter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class ReadingSessionQueryService {

    private final ReadingSessionRepository repository;

    public ReadingSessionListResponse findReadingSessions(ReadingSessionFilter filter) {
        var spec = ReadingSessionSpecification.byFilter(filter);
        List<ReadingSessionResponse> sessions = repository
            .findAll(spec)
            .stream()
            .map(ReadingSessionMapper::toReadingSessionResponse)
            .toList();
        return new ReadingSessionListResponse(sessions);
    }

    public boolean isSessionAuthor(Long sessionId, User user) {
        return repository.isAuthor(sessionId, user.getId());
    }

    ReadingSession findReadingSessionById(Long id) throws ReadingSessionNotFoundException {
        Optional<ReadingSession> sessionOpt = id != null ? repository.findById(id) : Optional.empty();
        return sessionOpt.orElseThrow(() -> {
            log.warn("ReadingSession not found with ID: {}", id);
            return new ReadingSessionNotFoundException(id);
        });
    }

}
