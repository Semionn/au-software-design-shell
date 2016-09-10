package com.au.mit.shell.common.command;

/**
 * Created by semionn on 10.09.16.
 */
public class Argument {
    private String name;
    private String value;

    public Argument(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
