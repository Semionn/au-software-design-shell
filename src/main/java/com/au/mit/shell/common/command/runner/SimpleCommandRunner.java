package com.au.mit.shell.common.command.runner;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.CommandResult;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

/**
 * Simple runner, which implements CommandRunner interface <p>
 * All commands running in calling thread
 */
public class SimpleCommandRunner implements CommandRunner {

    /**
     * Method for starting runner, should be called before command execution
     */
    @Override
    public void start() {

    }

    /**
     * Method for starting command
     */
    @Override
    public CommandResult run(Command command, PipedInputStream inputStream, List<Argument> args) {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream resultInputStream;
        try {
            resultInputStream = new PipedInputStream(outputStream);
        } catch (IOException e) {
            throw new UnknownError();
        }
        CommandResult result = new CommandResult(resultInputStream);
        command.run(inputStream, outputStream, args);
        return result;
    }

    /**
     * Method for stopping all yet running commands
     */
    @Override
    public void stop() {

    }
}
