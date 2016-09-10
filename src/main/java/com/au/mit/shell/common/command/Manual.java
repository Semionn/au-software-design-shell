package com.au.mit.shell.common.command;

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
        commandManual.put("exit", "exit - exit from the shell\n" +
                "exit");
    }

    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        if (args.size() > 0) {
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            try {
                dataOutputStream.writeChars(commandManual.getOrDefault(args.get(0).getValue(), "Command not found"));
                dataOutputStream.flush();
            } catch (IOException e) {
                throw new CommandException("", e);
            } finally {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    throw new CommandException("", e);
                }
            }
        } else {
            System.out.println("Not enough arguments");
        }
    }
}
