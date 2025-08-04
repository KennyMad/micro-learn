package org.pet.order.exception;

public class IllegalOrderOperationException extends RuntimeException {
    public IllegalOrderOperationException(String message) {
        super(message);
    }
}
