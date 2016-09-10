package com.au.mit.shell.common.command.runner;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.CommandResult;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

/**
 * Created by semionn on 10.09.16.
 */
public class MultiThreadCommandRunner implements CommandRunner {

    private ExecutorService executorService;
    private Supplier<ExecutorService> executorServiceGenerator;

    public MultiThreadCommandRunner(Supplier<ExecutorService> executorServiceGenerator) {
        this.executorServiceGenerator = executorServiceGenerator;
    }

    public MultiThreadCommandRunner(int threadCount) {
        this(() -> Executors.newFixedThreadPool(threadCount));
    }

    @Override
    public void start() {
        executorService = executorServiceGenerator.get();
    }

    @Override
    public CommandResult run(Command command, PipedInputStream inputStream, List<Argument> args) {
        PipedOutputStream outputStream = new PipedOutputStream();
        PipedInputStream resultInputStream;
        try {
            resultInputStream = new PipedInputStream(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CommandResult result = new CommandResult(resultInputStream);
        executorService.submit(() -> {
            try {
                command.run(inputStream, outputStream, args);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        });
        return result;
    }

    @Override
    public void stop() {
        executorService.shutdown();
    }
}
