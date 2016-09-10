package com.au.mit.shell.common.command;

import com.au.mit.shell.common.exceptions.CommandException;

import java.io.*;
import java.util.List;

/**
 * Created by semionn on 11.09.16.
 */
public class Pwd extends Command {
    public Pwd() {
        name = "pwd";
    }

    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        try {
            dataOutputStream.writeChars(System.getProperty("user.dir"));
            dataOutputStream.flush();
        } catch (IOException e) {
            throw new CommandException("", e);
        } finally {
            try {
                dataOutputStream.close();
            } catch (IOException e) {
                throw new CommandException("", e);
            }
        }
    }
}
