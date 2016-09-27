package com.au.mit.shell.common.scripts.runner;

import com.au.mit.shell.common.command.runner.CommandRunner;
import com.au.mit.shell.common.command.tasks.TaskFactory;
import com.au.mit.shell.common.scripts.ShellScript;

/**
 * Interface for executing ShellScripts
 */
public interface ShellScriptRunner {
    /**
     * Run ShellScripts with provided TaskFactory to creating Tasks from TaskDescription,
     * and execute them with CommandRunner
     */
    void run(ShellScript script, TaskFactory autoTaskFactory, CommandRunner commandRunner);
}
