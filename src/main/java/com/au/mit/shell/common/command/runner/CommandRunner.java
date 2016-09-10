package com.au.mit.shell.common.command.runner;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.CommandResult;

import java.io.PipedInputStream;
import java.util.List;

/**
 * Created by semionn on 10.09.16.
 */
public interface CommandRunner {
    CommandResult run(Command command, PipedInputStream inputStream, List<Argument> args);
    void stop();
    void start();
}
