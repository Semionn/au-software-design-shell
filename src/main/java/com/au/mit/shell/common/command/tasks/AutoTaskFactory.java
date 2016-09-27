package com.au.mit.shell.common.command.tasks;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.exceptions.UnknownCommand;

import java.util.List;

/**
 * Factory, which provides tasks with list of another TaskFactory objects <p>
 * Returns result of first successful call of one of TaskFactory objects set
 */
public class AutoTaskFactory implements TaskFactory {
    private List<TaskFactory> factories;

    /**
     * Class constructor with specified list of TaskFactory objects
     */
    public AutoTaskFactory(List<TaskFactory> factories) {
        this.factories = factories;
    }

    /**
     * Tries to create Task from arguments, returns Task if successful, null otherwise
     */
    @Override
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
