package com.au.mit.shell.common.command.tasks;

import com.au.mit.shell.common.command.Argument;

import java.util.List;

/**
 * Created by semionn on 10.09.16.
 */
public class TaskDescription {
    private String commandName;
    private List<Argument> args;

    public TaskDescription(String commandName, List<Argument> args) {
        this.commandName = commandName;
        this.args = args;
    }

    public String getCommandName() {
        return commandName;
    }

    public List<Argument> getArgs() {
        return args;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void setArgs(List<Argument> args) {
        this.args = args;
    }
}
