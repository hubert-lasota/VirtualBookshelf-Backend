package org.hl.wirtualnyregalbackend.application.book.exception;

import org.hl.wirtualnyregalbackend.application.common.OperationType;

public class IllegalBookOperationException extends RuntimeException {

    private final Long bookId;
    private final OperationType operationType;

    public IllegalBookOperationException(Long bookId, String operationType, String message) {
        super(message);
        this.bookId = bookId;
        this.operationType = OperationType.valueOf(operationType.toUpperCase());
    }

    public Long getBookId() {
        return bookId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

}
