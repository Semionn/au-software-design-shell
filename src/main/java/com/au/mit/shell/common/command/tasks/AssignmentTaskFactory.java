package com.au.mit.shell.common.command.tasks;

import com.au.mit.shell.common.Environment;
import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.embedded.Assignment;
import com.au.mit.shell.common.command.runner.CommandRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by semionn on 11.09.16.
 */
public class AssignmentTaskFactory implements TaskFactory {
    private CommandRunner commandRunner;
    private Environment environment;

    public AssignmentTaskFactory(CommandRunner commandRunner, Environment environment) {
        this.commandRunner = commandRunner;
        this.environment = environment;
    }

    @Override
    public Task tryCreate(String taskStr, List<Argument> args) {
        if (taskStr.contains("=")) {
            String[] splitted = taskStr.split("=");
            String varName = splitted[0];
            String value = "";
            if (splitted.length > 1) {
                value = splitted[1];
            }
            Argument[] arguments = { new Argument(varName, value) };
            return new Task(commandRunner, new Assignment(environment), Arrays.asList(arguments));
        }
        return null;
    }
}
