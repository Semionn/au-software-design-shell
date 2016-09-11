package com.au.mit.shell.common.command.tasks;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.exceptions.UnknownCommand;

import java.util.List;

/**
 * Created by semionn on 10.09.16.
 */
public class AutoTaskFactory implements TaskFactory {
    private List<TaskFactory> factories;

    public AutoTaskFactory(List<TaskFactory> factories) {
        this.factories = factories;
    }

    public Task tryCreate(String taskStr, List<Argument> args) {
        Task result = null;
        for (TaskFactory taskFactory : factories) {
            result = taskFactory.tryCreate(taskStr, args);
            if (result != null) {
                break;
            }
        }
        if (result == null) {
            throw new UnknownCommand(String.format("Command '%s' not recognized", taskStr));
        }
        return result;
    }
}
