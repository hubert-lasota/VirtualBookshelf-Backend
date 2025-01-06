package org.hl.wirtualnyregalbackend.application.bookshelf.exception;

import org.hl.wirtualnyregalbackend.application.common.OperationType;

public class IllegalBookshelfOperationException extends RuntimeException {

    private final Long bookshelfId;
    private final OperationType operationType;

    public IllegalBookshelfOperationException(Long bookshelfId, String operationType, String message) {
        super(message);
        this.bookshelfId = bookshelfId;
        this.operationType = OperationType.valueOf(operationType);
    }

    public Long getBookshelfId() {
        return bookshelfId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

}
