package com.au.mit.shell.common.scripts.runner;

import com.au.mit.shell.common.command.TaskCreator;
import com.au.mit.shell.common.command.runner.CommandRunner;
import com.au.mit.shell.common.scripts.ShellScript;

/**
 * Created by semionn on 10.09.16.
 */
public interface ShellScriptRunner {
    void run(ShellScript script, TaskCreator taskCreator, CommandRunner commandRunner);
}