package com.au.mit.shell.common.command.embedded;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.exceptions.CommandException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

import static com.au.mit.shell.common.command.PipelineUtils.defaultCharset;

/**
 * Created by semionn on 11.09.16.
 */
public class Pwd implements Command {
    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        BufferedOutputStream dataOutputStream = new BufferedOutputStream(outputStream);
        try {
            dataOutputStream.write(System.getProperty("user.dir").getBytes(defaultCharset()));
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

    @Override
    public String getName() {
        return "pwd";
    }
}
