package com.au.mit.shell.common;

import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.TaskCreator;
import com.au.mit.shell.common.command.runner.CommandRunner;
import com.au.mit.shell.common.parser.ShellParser;
import com.au.mit.shell.common.scripts.ShellScript;
import com.au.mit.shell.common.scripts.runner.ShellScriptRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Created by semionn on 10.09.16.
 */
public class Shell {
    private ShellParser parser;
    private ShellScriptRunner scriptRunner;
    private TaskCreator taskCreator;
    private CommandRunner commandRunner;

    public Shell(ShellParser parser, ShellScriptRunner scriptRunner, CommandRunner commandRunner,
                 Command[] commands) {
        this.parser = parser;
        this.scriptRunner = scriptRunner;
        this.commandRunner = commandRunner;
        taskCreator = new TaskCreator(commandRunner, commands);
    }

    public void start() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while (!Objects.equals(line = br.readLine(), "exit")) {
                ShellScript script = parser.parse(line);
                scriptRunner.run(script, taskCreator, commandRunner);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
