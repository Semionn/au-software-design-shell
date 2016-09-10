package com.au.mit.shell.common.command;

import com.au.mit.shell.common.command.runner.CommandRunner;
import com.au.mit.shell.common.exceptions.UnknownCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by semionn on 10.09.16.
 */
public class TaskCreator {
    private Map<String, Command> commandMap;
    private CommandRunner commandRunner;

    public TaskCreator(CommandRunner commandRunner, Command[] commands) {
        Map<String, Command> commandMap = new HashMap<>();
        for (Command cmd : commands) {
            commandMap.put(cmd.getName(), cmd);
        }
        this.commandMap = commandMap;
        this.commandRunner = commandRunner;
    }

    public Task create(String taskStr, List<Argument> args) {
        String[] tokens = taskStr.split(" ");
        String commandName = tokens[0];
        Command command = findCommand(commandName);
        if (command == null) {
            throw new UnknownCommand(String.format("Command '%s' not found", commandName));
        }
        return new Task(commandRunner, command, args);
    }

    private Command findCommand(String name) {
        return commandMap.getOrDefault(name, null);
    }
}
