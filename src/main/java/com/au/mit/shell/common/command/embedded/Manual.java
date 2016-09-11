package com.au.mit.shell.common.command.embedded;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.exceptions.CommandException;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by semionn on 10.09.16.
 */
public class Manual extends Command {
    private Map<String, String> commandManual;

    public Manual() {
        name = "man";
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

    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        try {
            if (args.size() > 0) {
                dataOutputStream.writeChars(commandManual.getOrDefault(args.get(0).getValue(), "Command not found"));
                dataOutputStream.flush();
            } else {
                dataOutputStream.writeChars("Not enough arguments");
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
}
