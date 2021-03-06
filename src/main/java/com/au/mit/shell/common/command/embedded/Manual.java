package com.au.mit.shell.common.command.embedded;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.exceptions.CommandException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.au.mit.shell.common.command.PipelineUtils.defaultCharset;

/**
 * Shell command for printing out manual for specified command
 */
public class Manual implements Command {
    private final Map<String, String> commandManual;

    /**
     * Class constructor
     */
    public Manual() {
        commandManual = new HashMap<>();
        commandManual.put("cat", "cat - prints file to standard output\n" +
                "cat [FILE]");
        commandManual.put("wc", "wc - prints counts of lines, words and chars to standard output\n" +
                "wc [FILE]");
        commandManual.put("man", "man - prints manual about command\n" +
                "man [COMMAND]");
        commandManual.put("pwd", "pwd - prints current working directory to standard output\n" +
                "pwd");
        commandManual.put("echo", "echo - prints arguemtn to standard output\n" +
                "echo");
        commandManual.put("exit", "exit - exit from the shell\n" +
                "exit");
    }

    /**
     * Overridden Command method to print manual with input as PipedInputStream and list of arguments,
     * and output as PipedOutputStream
     */
    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        BufferedOutputStream dataOutputStream = new BufferedOutputStream(outputStream);
        try {
            if (args.size() > 0) {
                String text = commandManual.getOrDefault(args.get(0).getValue(), "Command not found");
                dataOutputStream.write(text.getBytes(defaultCharset()));
                dataOutputStream.flush();
            } else {
                dataOutputStream.write("Not enough arguments".getBytes(defaultCharset()));
            }
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
        return "man";
    }
}
