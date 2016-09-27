package com.au.mit.shell.common.exceptions;

/**
 * Exception class for case, when parsed command not recognized by the Shell
 */
public class UnknownCommand extends RuntimeException {
    public UnknownCommand(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownCommand(String message) {
        super(message);
    }
}
