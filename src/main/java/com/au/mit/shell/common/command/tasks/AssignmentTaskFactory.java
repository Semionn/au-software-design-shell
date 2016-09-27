package com.au.mit.shell.common.command.tasks;

import com.au.mit.shell.common.Environment;
import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.embedded.Assignment;
import com.au.mit.shell.common.command.runner.CommandRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Factory, which provides tasks with Assignment command
 */
public class AssignmentTaskFactory implements TaskFactory {
    private CommandRunner commandRunner;
    private Environment environment;

    /**
     * Class constructor with command runner and environment specified
     */
    public AssignmentTaskFactory(CommandRunner commandRunner, Environment environment) {
        this.commandRunner = commandRunner;
        this.environment = environment;
    }

    /**
     * Tries to create Task from arguments, returns Task if successful, null otherwise
     */
    @Override
    public Task tryCreate(String taskStr, List<Argument> args) {
        if (taskStr.contains("=")) {
            return new Task(commandRunner, new Assignment(environment), args);
        }
        return null;
    }
}
