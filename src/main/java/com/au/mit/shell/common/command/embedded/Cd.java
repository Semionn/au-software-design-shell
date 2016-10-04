package com.au.mit.shell.common.command.embedded;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.exceptions.CommandException;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Shell command for changing working directory.
 */
public class Cd implements Command {

    /**
     * Resolves next working directory applying passed argument to current working directory.
     * If new directory doesn't exist, exception is thrown.
     */
    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        String workingDirectory = System.getProperty("user.dir");
        String directoryChange = args.get(0).getValue();
        Path newPath = Paths.get(workingDirectory).resolve(directoryChange);
        if (!Files.exists(newPath) || !Files.isDirectory(newPath)) {
            throw new CommandException("No such directory: " + newPath);
        }
        System.setProperty("user.dir", newPath.toString());

        try {
            outputStream.close(); // didn't work without closing :(
        } catch (IOException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }

    @Override
    public String getName() {
        return "cd";
    }
}
