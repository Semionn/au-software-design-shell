package com.au.mit.shell.common.command.tasks;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.embedded.ExternalProcessCmd;
import com.au.mit.shell.common.command.runner.CommandRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Factory, which provides tasks with ExternalProcessCmd command
 */
public class ExternalProcessTaskFactory implements TaskFactory {
    private CommandRunner commandRunner;

    /**
     * Class constructor with command runner specified
     */
    public ExternalProcessTaskFactory(CommandRunner commandRunner) {
        this.commandRunner = commandRunner;
    }

    /**
     * Tries to create Task from arguments, returns Task if successful, null otherwise
     */
    @Override
    public Task tryCreate(String taskStr, List<Argument> args) {
        args.add(0, new Argument("", taskStr));
        return new Task(commandRunner, new ExternalProcessCmd(), args);
    }
}
