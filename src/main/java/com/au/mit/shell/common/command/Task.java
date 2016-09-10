package com.au.mit.shell.common.command;

import com.au.mit.shell.common.command.runner.CommandRunner;

import java.io.PipedInputStream;
import java.util.List;

/**
 * Created by semionn on 10.09.16.
 */
public class Task {
    private CommandRunner commandRunner;
    private Command command;
    private List<Argument> args;

    public Task(CommandRunner commandRunner, Command command, List<Argument> args) {
        this.commandRunner = commandRunner;
        this.command = command;
        this.args = args;
    }

    public CommandResult run(CommandResult previosResult) {
        PipedInputStream stream = null;
        if (previosResult != null) {
            stream = previosResult.getStream();
        }
        return commandRunner.run(command, stream, args);
    }
}
