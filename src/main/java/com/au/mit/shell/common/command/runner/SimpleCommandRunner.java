package com.au.mit.shell.common.command.runner;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.CommandResult;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

/**
 * Created by semionn on 10.09.16.
 */
public class SimpleCommandRunner implements CommandRunner {

    @Override
    public void start() {

    }

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

    @Override
    public void stop() {

    }
}
