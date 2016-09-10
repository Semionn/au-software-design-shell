package com.au.mit.shell.common.scripts.runner;

import com.au.mit.shell.common.command.CommandResult;
import com.au.mit.shell.common.command.Task;
import com.au.mit.shell.common.command.TaskCreator;
import com.au.mit.shell.common.command.runner.CommandRunner;
import com.au.mit.shell.common.scripts.ShellScript;
import com.au.mit.shell.common.scripts.TaskDescription;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;

/**
 * Created by semionn on 10.09.16.
 */
public class ShellScriptSimpleRunner implements ShellScriptRunner {

    @Override
    public void run(ShellScript script, TaskCreator taskCreator, CommandRunner commandRunner) {
        try {
            commandRunner.start();
            CommandResult lastResult = null;
            for (TaskDescription taskDescription : script.getTasks()) {
                Task task = taskCreator.create(taskDescription.getCommandName(), taskDescription.getArgs());
                lastResult = task.run(lastResult);
            }
            if (lastResult != null) {
                printResult(lastResult);
            }
        } catch (Throwable e) {
            e.printStackTrace();
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
