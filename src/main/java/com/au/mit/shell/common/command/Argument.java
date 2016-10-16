package com.au.mit.shell.common.command;

/**
 * Stores argument name and value for Commands
 */
public class Argument {
    private final String name;
    private final String value;

    /**
     * Class constructor with argument name and value specified
     */
    public Argument(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns argument name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns argument value
     */
    public String getValue() {
        return value;
    }
}
