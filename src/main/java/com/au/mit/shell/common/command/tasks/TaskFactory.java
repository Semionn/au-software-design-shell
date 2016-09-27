package com.au.mit.shell.common.command.tasks;

import com.au.mit.shell.common.command.Argument;

import java.util.List;

/**
 * Abstract factory, which provides tasks
 */
public interface TaskFactory {
    /**
     * Tries to create Task from arguments, returns Task if successful, null otherwise
     */
    Task tryCreate(String taskStr, List<Argument> args);
}
