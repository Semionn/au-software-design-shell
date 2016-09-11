package com.au.mit.shell.common.command.embedded;

import com.au.mit.shell.common.Environment;
import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.exceptions.CommandException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

/**
 * Created by semionn on 11.09.16.
 */
public class Assignment extends Command {
    private Environment environment;
    public Assignment(Environment environment) {
        name = "assignment";
        this.environment = environment;
    }

    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        try {
            if (args.size() < 1) {
                dataOutputStream.writeChars("Not enough arguments");
                dataOutputStream.flush();
                return;
            }
            environment.addVariable(args.get(0).getName(), args.get(0).getValue());
        } catch (IOException e) {
            throw new CommandException(e.getMessage(), e);
        } finally {
            try {
                dataOutputStream.close();
            } catch (IOException e) {
                throw new CommandException(e.getMessage(), e);
            }
        }
    }
}
