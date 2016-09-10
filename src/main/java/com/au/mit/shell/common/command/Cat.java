package com.au.mit.shell.common.command;

import com.au.mit.shell.common.exceptions.CommandException;

import java.io.*;
import java.util.List;

/**
 * Created by semionn on 10.09.16.
 */
public class Cat extends Command {
    public Cat() {
        name = "cat";
    }

    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        final int BUFFER_SIZE = 1024;
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
            try {
                char[] buffer = new char[BUFFER_SIZE];
                int len;
                while ((len = br.read(buffer)) > 0) {
                    dataOutputStream.writeChars(new String(buffer).substring(0, len));
                }
            } finally {
                br.close();
            }
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
