package com.au.mit.shell.common.command.tasks;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.CommandResult;
import com.au.mit.shell.common.command.runner.CommandRunner;

import java.io.PipedInputStream;
import java.util.List;

/**
 * Set of command and it's arguments <p>
 * Allows run the command
 */
public class Task {
    private CommandRunner commandRunner;
    private Command command;
    private List<Argument> args;

    /**
     * Class constructor with command runner, command and list of it's arguments specified
     */
    public Task(CommandRunner commandRunner, Command command, List<Argument> args) {
        this.commandRunner = commandRunner;
        this.command = command;
        this.args = args;
    }

    /**
     * Run stored command, with piped input from CommandResult
     */
    public CommandResult run(CommandResult previosResult) {
        PipedInputStream stream = null;
        if (previosResult != null) {
            stream = previosResult.getStream();
        }
        return commandRunner.run(command, stream, args);
    }
}
