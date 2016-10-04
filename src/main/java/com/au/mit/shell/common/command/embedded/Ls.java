package com.au.mit.shell.common.command.embedded;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;

import java.io.File;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Shell command for printing current directory contents.
 */
public class Ls implements Command {
    /**
     * Gets all files in current directory and prints them to outputStream.
     */
    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        try (PrintWriter writer = new PrintWriter(outputStream)) {
            String[] files = new File(System.getProperty("user.dir")).list();
            if (files != null) {
                for (String file : files) {
                    writer.println(file);
                }
            }
        }
    }

    @Override
    public String getName() {
        return "ls";
    }
}
