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
 * MultiThread runner, which implements CommandRunner interface <p>
 * Allows start multiple commands in different threads, and connect piped commands
 * with PipedInputStream and PipedOutputStream
 */
public class MultiThreadCommandRunner implements CommandRunner {

    private ExecutorService executorService;
    private Supplier<ExecutorService> executorServiceGenerator;

    /**
     * Class constructor with ExecutorService specified
     */
    public MultiThreadCommandRunner(Supplier<ExecutorService> executorServiceGenerator) {
        this.executorServiceGenerator = executorServiceGenerator;
    }

    /**
     * Class constructor with specified number of threads in thread pool
     */
    public MultiThreadCommandRunner(int threadCount) {
        this(() -> Executors.newFixedThreadPool(threadCount));
    }

    /**
     * Method for starting runner, should be called before command execution
     */
    @Override
    public void start() {
        executorService = executorServiceGenerator.get();
    }

    /**
     * Method for starting command in thread from thread pool
     */
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
                System.out.println(String.format("%s: %s", e.getClass().toString(), e.getMessage()));
                try {
                    resultInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        return result;
    }
    /**
     * Method for stopping all yet running commands
     */
    @Override
    public void stop() {
        executorService.shutdown();
    }
}
