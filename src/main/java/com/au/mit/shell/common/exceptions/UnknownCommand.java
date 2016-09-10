package com.au.mit.shell.common.exceptions;

/**
 * Created by semionn on 10.09.16.
 */
public class UnknownCommand extends RuntimeException {
    public UnknownCommand(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownCommand(String message) {
        super(message);
    }
}
