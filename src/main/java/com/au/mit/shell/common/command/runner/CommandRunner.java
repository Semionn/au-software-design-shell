package com.au.mit.shell.common.command.runner;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.CommandResult;

import java.io.PipedInputStream;
import java.util.List;

/**
 * Interface for command executing
 */
public interface CommandRunner {

    /**
     * Method for starting command
     */
    CommandResult run(Command command, PipedInputStream inputStream, List<Argument> args);

    /**
     * Method for stopping all yet running commands
     */
    void stop();

    /**
     * Method for starting runner, should be called before command execution
     */
    void start();
}
