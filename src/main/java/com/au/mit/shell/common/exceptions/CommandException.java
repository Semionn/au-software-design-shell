package com.au.mit.shell.common.exceptions;

/**
 * Exception class wrapper for exceptions have occurred during a command execution
 */
public class CommandException extends RuntimeException {
    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(String message) {
        super(message);
    }
}
