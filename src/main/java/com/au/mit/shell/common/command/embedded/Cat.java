package com.au.mit.shell.common.command.embedded;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.PipelineUtils;
import com.au.mit.shell.common.exceptions.CommandException;

import java.io.*;
import java.util.List;

import static com.au.mit.shell.common.command.PipelineUtils.defaultCharset;

/**
 * Shell command for printing out specified file content or piped input
 */
public class Cat implements Command {
    /**
     * Overridden Command method to process Assignment with input as PipedInputStream and list of arguments,
     * and output as PipedOutputStream
     */
    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        try {
            InputStream is;
            if (args.size() > 0) {
                is = new FileInputStream(args.get(0).getValue());
            } else if (inputStream != null) {
                is = inputStream;
            } else {
                BufferedOutputStream dataOutputStream = new BufferedOutputStream(outputStream);
                dataOutputStream.write("Not enough arguments".getBytes(defaultCharset()));
                dataOutputStream.flush();
                dataOutputStream.close();
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
        return "cat";
    }
}
