package com.au.mit.shell.common.command.tasks;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.runner.CommandRunner;
import com.au.mit.shell.common.exceptions.UnknownCommand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by semionn on 11.09.16.
 */
public class EmbeddedTaskFactory implements TaskFactory {
    private CommandRunner commandRunner;
    private Map<String, Command> commandMap;

    public EmbeddedTaskFactory(CommandRunner commandRunner, Command[] commands) {
        Map<String, Command> commandMap = new HashMap<>();
        for (Command cmd : commands) {
            commandMap.put(cmd.getName(), cmd);
        }
        this.commandMap = commandMap;
        this.commandRunner = commandRunner;
    }

    @Override
    public Task tryCreate(String taskStr, List<Argument> args) {
        String commandName = taskStr;
        Command command = findCommand(commandName);
        if (command == null) {
            return null;
        }
        return new Task(commandRunner, command, args);
    }


    private Command findCommand(String name) {
        return commandMap.getOrDefault(name, null);
    }
}
