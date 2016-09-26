package com.au.mit.shell.common.command;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

/**
 * Created by semionn on 10.09.16.
 */
public interface Command {
    void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args);

    String getName();
}
