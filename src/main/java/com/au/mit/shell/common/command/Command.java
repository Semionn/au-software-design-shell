package com.au.mit.shell.common.command;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

/**
 * Interface for shell commands
 */
public interface Command {

    /**
     * Run command with input as PipedInputStream and arguments, and output as PipedOutputStream
     */
    void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args);

    /**
     * Returns command name
     */
    String getName();
}
