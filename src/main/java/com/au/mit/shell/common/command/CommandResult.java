package com.au.mit.shell.common.command;


import java.io.PipedInputStream;

/**
 * Created by semionn on 10.09.16.
 */
public class CommandResult {
    private PipedInputStream stream = new PipedInputStream();

    public CommandResult(PipedInputStream stream) {
        this.stream = stream;
    }

    public PipedInputStream getStream() {
        return stream;
    }
}
