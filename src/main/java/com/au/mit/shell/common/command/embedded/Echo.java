package com.au.mit.shell.common.command.embedded;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.PipelineUtils;
import com.au.mit.shell.common.exceptions.CommandException;

import java.io.*;
import java.util.List;

/**
 * Shell command for printing out specified argument or piped input
 */
public class Echo implements Command {
    /**
     * Overridden Command method to process Echo with input as PipedInputStream and list of arguments,
     * and output as PipedOutputStream
     */
    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        try {
            InputStream is;
            if (args.size() > 0) {
                is = new ByteArrayInputStream(args.get(0).getValue().getBytes());
            } else if (inputStream != null) {
                is = inputStream;
            } else {
                outputStream.close();
                return;
            }
            PipelineUtils.ConnectStreams(is, outputStream);
        } catch (IOException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    /**
     * Returns name of command
     */
    @Override
    public String getName() {
        return "echo";
    }
}
