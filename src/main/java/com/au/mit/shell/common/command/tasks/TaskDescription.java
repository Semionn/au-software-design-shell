package com.au.mit.shell.common.command.tasks;

import com.au.mit.shell.common.command.Argument;

import java.util.List;

/**
 * Provides parsed description of command and it's arguments
 */
public class TaskDescription {
    private String commandName;
    private List<Argument> args;

    /**
     * Class constructor with specified command name and list of arguments
     */
    public TaskDescription(String commandName, List<Argument> args) {
        this.commandName = commandName;
        this.args = args;
    }

    /**
     * Returns command name
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Returns list of arguments
     */
    public List<Argument> getArgs() {
        return args;
    }

    /**
     * Set up command name
     */
    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    /**
     * Set up list of arguments
     */
    public void setArgs(List<Argument> args) {
        this.args = args;
    }
}
