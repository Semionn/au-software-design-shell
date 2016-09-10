package com.au.mit.shell.common.exceptions;

/**
 * Created by semionn on 10.09.16.
 */
public class CommandException extends RuntimeException {
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(String message) {
        super(message);
    }
}
