package com.au.mit.shell.common.command.embedded;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.PipelineUtils;
import com.au.mit.shell.common.exceptions.CommandException;

import java.io.*;
import java.util.List;

/**
 * Created by semionn on 11.09.16.
 */
public class Echo extends Command {
    public Echo() {
        name = "echo";
    }

    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        try {
            InputStream is;
            if (args.size() > 0) {
                is = new ByteArrayInputStream(args.get(0).getValue().getBytes());
            } else if (inputStream != null) {
                is = inputStream;
            } else {
                return;
            }
            PipelineUtils.ConnectStreams(is, outputStream);
        } catch (IOException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
