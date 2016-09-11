package com.au.mit.shell.common.command.tasks;

import com.au.mit.shell.common.command.Argument;

import java.util.List;

/**
 * Created by semionn on 11.09.16.
 */
public interface TaskFactory {
    Task tryCreate(String taskStr, List<Argument> args);
}
