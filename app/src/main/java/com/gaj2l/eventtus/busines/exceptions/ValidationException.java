package com.gaj2l.eventtus.busines.exceptions;

/**
 * Created by Jackson Majolo on 26/03/2017.
 */

public class ValidationException extends Exception {
    private final int messageResource;

    public ValidationException(int messageResource) {
        this.messageResource = messageResource;
    }

    public int getMessageResource() {
        return messageResource;
    }
}
