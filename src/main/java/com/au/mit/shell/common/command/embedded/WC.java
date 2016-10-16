package com.au.mit.shell.common.command.embedded;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.exceptions.CommandException;

import java.io.*;
import java.util.List;

import static com.au.mit.shell.common.command.PipelineUtils.defaultCharset;

/**
 * Shell command for printing out chars, words and lines count for specified file or piped input
 */
public class WC implements Command {
    /**
     * Overridden Command method to print out char, word and lines count with input as PipedInputStream
     * and list of arguments, and output as PipedOutputStream
     */
    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        BufferedOutputStream dataOutputStream = new BufferedOutputStream(outputStream);
        try {
            BufferedReader br;
            if (args.size() > 0) {
                br = new BufferedReader(new FileReader(args.get(0).getValue()));
            } else if (inputStream != null) {
                br = new BufferedReader(new InputStreamReader(inputStream));
            } else {
                dataOutputStream.write("Not enough arguments".getBytes(defaultCharset()));
                return;
            }
            int lineCount = 0;
            int wordCount = 0;
            int charCount = 0;
            try {
                String s;
                while ((s = br.readLine()) != null) {
                    lineCount++;
                    wordCount += s.trim().split(" ").length;
                    charCount += s.length();
                }
            } finally {
                br.close();
            }
            String result = String.format("%d %d %d", lineCount, wordCount, charCount);
            dataOutputStream.write(result.getBytes(defaultCharset()));
        } catch (IOException e) {
            throw new CommandException(e.getMessage(), e);
        } finally {
            try {
                dataOutputStream.flush();
                dataOutputStream.close();
            } catch (IOException e) {
                throw new CommandException(e.getMessage(), e);
            }
        }
    }

    /**
     * Returns name of the command
     */
    @Override
    public String getName() {
        return "wc";
    }

}
