package org.hl.wirtualnyregalbackend.application.author.exception;

import org.hl.wirtualnyregalbackend.application.common.OperationType;

public class IllegalAuthorOperationException extends RuntimeException {

    private final Long authorId;
    private final OperationType operationType;

    public IllegalAuthorOperationException(Long authorId, String operationType, String message) {
        super(message);
        this.authorId = authorId;
        this.operationType = OperationType.valueOf(operationType.toUpperCase());
    }

    public Long getAuthorId() {
        return authorId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

}
