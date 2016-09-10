package com.au.mit.shell.common.command;

import com.au.mit.shell.common.exceptions.CommandException;

import java.io.*;
import java.util.List;

/**
 * Created by semionn on 10.09.16.
 */
public class WC extends Command {

    public WC() {
        name = "wc";
    }

    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        try {
            BufferedReader br;
            if (args.size() > 0) {
                br = new BufferedReader(new FileReader(args.get(0).getValue()));
            } else if (inputStream != null) {
                br = new BufferedReader(new InputStreamReader(inputStream));
            } else {
                System.out.println("Not enough arguments");
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
            dataOutputStream.writeChars(String.format("%d %d %d", lineCount, wordCount, charCount));
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
    }
}
