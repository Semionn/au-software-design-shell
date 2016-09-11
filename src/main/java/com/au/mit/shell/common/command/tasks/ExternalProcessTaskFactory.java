package com.au.mit.shell.common.command.tasks;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.embedded.ExternalProcessCmd;
import com.au.mit.shell.common.command.runner.CommandRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by semionn on 11.09.16.
 */
public class ExternalProcessTaskFactory implements TaskFactory {
    private CommandRunner commandRunner;

    public ExternalProcessTaskFactory(CommandRunner commandRunner) {
        this.commandRunner = commandRunner;
    }

    @Override
    public Task tryCreate(String taskStr, List<Argument> args) {
        args.add(0, new Argument("", taskStr));
        return new Task(commandRunner, new ExternalProcessCmd(), args);
    }
}
