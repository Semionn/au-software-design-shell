package com.au.mit.shell.common;

import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.tasks.*;
import com.au.mit.shell.common.command.runner.CommandRunner;
import com.au.mit.shell.common.parser.ShellParser;
import com.au.mit.shell.common.scripts.ShellScript;
import com.au.mit.shell.common.scripts.runner.ShellScriptRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Main class of the Shell <p>
 * Allows execute multiple commands in console style
 */
public class Shell {
    private ShellParser parser;
    private ShellScriptRunner scriptRunner;
    private AutoTaskFactory autoTaskFactory;
    private CommandRunner commandRunner;
    private Environment environment;

    /**
     * Class constructor <p>
     * Requires ShellParser, ShellScriptRunner, CommandRunner and array of allowed embedded commands
     */
    public Shell(ShellParser parser, ShellScriptRunner scriptRunner, CommandRunner commandRunner,
                 Command[] commands) {
        this.parser = parser;
        this.scriptRunner = scriptRunner;
        this.commandRunner = commandRunner;
        environment = new Environment();
        List<TaskFactory> factories = new ArrayList<>();
        factories.add(new EmbeddedTaskFactory(commandRunner, commands));
        factories.add(new AssignmentTaskFactory(commandRunner, environment));
        factories.add(new ExternalProcessTaskFactory(commandRunner));
        autoTaskFactory = new AutoTaskFactory(factories);
    }

    /**
     * Start the shell <p>
     * Executes printed commands until the line "exit" will not be printed
     */
    public void start() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line;
            System.out.print("> ");
            while (!Objects.equals(line = br.readLine(), "exit")) {
                ShellScript script = parser.parse(line);
                environment.replaceVariables(script);
                scriptRunner.run(script, autoTaskFactory, commandRunner);
                System.out.print("> ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
