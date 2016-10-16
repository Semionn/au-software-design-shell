package com.au.mit.shell.common.command;


import java.io.PipedInputStream;

/**
 * Result of command execution <p>
 * Stores output stream of command
 */
public class CommandResult {
    private PipedInputStream stream = new PipedInputStream();

    /**
     * Class constructor with PipedInputStream argument, which should be connected with a command's PipedOutputStream
     */
    public CommandResult(PipedInputStream stream) {
        this.stream = stream;
    }

    /**
     * Returns stored PipedInputStream
     */
    public PipedInputStream getStream() {
        return stream;
    }
}
