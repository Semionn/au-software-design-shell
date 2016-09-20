package com.au.mit.shell.common;

import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.embedded.Echo;
import com.au.mit.shell.common.command.runner.SimpleCommandRunner;
import com.au.mit.shell.common.parser.ShellParser;
import com.au.mit.shell.common.scripts.runner.ShellScriptSimpleRunner;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by semionn on 15.09.16.
 */
public class ShellTest {

    @Test
    public void simpleRunnerEchoTest() throws Exception {
        final Command[] commands = {new Echo()};
        final Shell shell = new Shell(new ShellParser(), new ShellScriptSimpleRunner(),
                new SimpleCommandRunner(), commands);

        final String testString1 = "testOne";
        final String testString2 = "testTwo";
        final String input = "echo '" + testString1 + "'" + getEndLine() +
                "echo '" + testString2 + "'" + getEndLine() + "exit";
        testCommand(shell, testString1, testString2, input);
    }

    @Test
    public void simpleRunnerAssignmentTest() throws Exception {
        final Command[] commands = { new Echo() };
        final Shell shell = new Shell(new ShellParser(), new ShellScriptSimpleRunner(),
                new SimpleCommandRunner(), commands);

        final String testString1 = "testOne";
        final String input = "x='" + testString1 + "'" + getEndLine() +
                "echo $x" + getEndLine() + "exit";
        testCommand(shell, "", testString1, input);
    }

    private void testCommand(Shell shell, String testString1, String testString2, String input) throws IOException {
        try (ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             PrintStream out = new PrintStream(byteArrayOutputStream, true, "UTF-8")) {

            try {
                System.setIn(in);
                System.setOut(out);
                shell.start();
            } finally {
                System.setIn(System.in);
                System.setOut(System.out);
            }

            final String shellOutput = byteArrayOutputStream.toString("UTF-8");
            final int shellIndentLen = 2;
            final int endLen = getEndLine().length();
            assertEquals(testString1, shellOutput.substring(shellIndentLen, shellIndentLen + testString1.length()));
            assertEquals(testString2, shellOutput.substring(shellIndentLen * 2 + testString1.length() + endLen,
                    shellIndentLen * 2 + testString1.length() + endLen + testString2.length()));
        }
    }

    private String getEndLine() {
        return System.getProperty("line.separator");
    }
}