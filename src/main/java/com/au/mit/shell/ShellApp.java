package com.au.mit.shell;

import com.au.mit.shell.common.Shell;
import com.au.mit.shell.common.command.embedded.Cd;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.embedded.Ls;
import com.au.mit.shell.common.command.embedded.*;
import com.au.mit.shell.common.command.runner.MultiThreadCommandRunner;
import com.au.mit.shell.common.parser.ShellParser;
import com.au.mit.shell.common.scripts.runner.ShellScriptSimpleRunner;

/**
 * Class which creates and start the Shell with some default parameters
 */
public class ShellApp {

    /**
     * Creates and start the Shell with commands: cat, wc, man, pwd, echo, cd and ls <p>
     * Allows unlimited streams of data in pipes (3 threads per script)
     */
    public static void main(String[] args) {
        Command[] commands = {
                new Cat(), new WC(), new Manual(),
                new Pwd(), new Echo(), new Grep(),
                new Cd(), new Ls()
        };
        new Shell(new ShellParser(), new ShellScriptSimpleRunner(),
                  new MultiThreadCommandRunner(3), commands).start();
    }
}
