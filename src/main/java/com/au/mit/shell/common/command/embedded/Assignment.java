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
        BufferedOutputStream dataOutputStream = new BufferedOutputStream(outputStream);
        try {
            if (args.size() < 1) {
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
}
