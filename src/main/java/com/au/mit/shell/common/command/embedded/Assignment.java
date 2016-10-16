package com.au.mit.shell.common.command.embedded;

import com.au.mit.shell.common.Environment;
import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.exceptions.CommandException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

import static com.au.mit.shell.common.command.PipelineUtils.defaultCharset;
import static com.au.mit.shell.common.command.PipelineUtils.getEndLine;

/**
 * Shell command for assign text value to environment variable
 */
public class Assignment implements Command {
    private final Environment environment;

    /**
     * Constructor, environment argument required
     */
    public Assignment(Environment environment) {
        this.environment = environment;
    }

    /**
     * Overridden Command method to process Assignment with input as PipedInputStream and list of arguments,
     * and output as PipedOutputStream
     */
    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        BufferedOutputStream dataOutputStream = new BufferedOutputStream(outputStream);
        try {
            if (args.size() < 2) {
                dataOutputStream.write("Not enough arguments".getBytes(defaultCharset()));
                dataOutputStream.flush();
                return;
            }
            environment.addVariable(args.get(0).getValue(), args.get(1).getValue());
            dataOutputStream.write(getEndLine().getBytes(defaultCharset()));
            dataOutputStream.flush();
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

    /**
     * Returns name of command
     */
    @Override
    public String getName() {
        return "assignment";
    }
}
