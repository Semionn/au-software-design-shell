package com.au.mit.shell.common.scripts.runner;

import com.au.mit.shell.common.command.tasks.AutoTaskFactory;
import com.au.mit.shell.common.command.runner.CommandRunner;
import com.au.mit.shell.common.command.tasks.TaskFactory;
import com.au.mit.shell.common.scripts.ShellScript;

/**
 * Created by semionn on 10.09.16.
 */
public interface ShellScriptRunner {
    void run(ShellScript script, TaskFactory autoTaskFactory, CommandRunner commandRunner);
}
