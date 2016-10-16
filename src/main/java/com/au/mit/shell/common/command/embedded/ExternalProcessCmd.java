package com.au.mit.shell.common.command.embedded;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.PipelineUtils;
import com.au.mit.shell.common.exceptions.CommandException;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Shell command for calling external process
 */
public class ExternalProcessCmd implements Command {
    /**
     * Overridden Command method to call external process with input as PipedInputStream and list of arguments,
     * and output as PipedOutputStream
     */
    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        ProcessBuilder pb = new ProcessBuilder(args.stream().map(Argument::getValue).collect(Collectors.toList()));
        try {
            Process process = pb.start();
            if (inputStream != null) {
                PipelineUtils.ConnectStreams(inputStream, process.getOutputStream());
            }
            PipelineUtils.ConnectStreams(process.getInputStream(), outputStream);
        } catch (IOException e) {
            throw new CommandException(e.getMessage(), e);
        } finally {
            try {
                outputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Returns name of the command
     */
    @Override
    public String getName() {
        return "externalProcess";
    }
}
