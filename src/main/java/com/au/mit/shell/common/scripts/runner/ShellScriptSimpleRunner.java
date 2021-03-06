package com.au.mit.shell.common.scripts.runner;

import com.au.mit.shell.common.command.CommandResult;
import com.au.mit.shell.common.command.tasks.Task;
import com.au.mit.shell.common.command.tasks.AutoTaskFactory;
import com.au.mit.shell.common.command.runner.CommandRunner;
import com.au.mit.shell.common.command.tasks.TaskFactory;
import com.au.mit.shell.common.scripts.ShellScript;
import com.au.mit.shell.common.command.tasks.TaskDescription;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;

/**
 * Straightforward implementation of ShellScriptRunner interface: starts tasks consequently, one by one
 */
public class ShellScriptSimpleRunner implements ShellScriptRunner {

    /**
     * Run ShellScript with provided TaskFactory and CommandRunner <p>
     * All occurred exceptions during execution will be caught and printed to standard output
     */
    @Override
    public void run(ShellScript script, TaskFactory taskFactory, CommandRunner commandRunner) {
        try {
            commandRunner.start();
            CommandResult lastResult = null;
            for (TaskDescription taskDescription : script.getPipedTasks()) {
                Task task = taskFactory.tryCreate(taskDescription.getCommandName(), taskDescription.getArgs());
                lastResult = task.run(lastResult);
            }
            if (lastResult != null) {
                printResult(lastResult);
            }
        } catch (Throwable e) {
            System.out.println(String.format("%s: %s", e.getClass().toString(), e.getMessage()));
        } finally {
            commandRunner.stop();
        }
    }

    private void printResult(CommandResult lastResult) {
        PipedInputStream inputStream = lastResult.getStream();
        String line;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
