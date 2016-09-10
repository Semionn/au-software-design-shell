package com.au.mit.shell;

import com.au.mit.shell.common.Shell;
import com.au.mit.shell.common.command.*;
import com.au.mit.shell.common.command.runner.MultiThreadCommandRunner;
import com.au.mit.shell.common.parser.ShellParser;
import com.au.mit.shell.common.scripts.runner.ShellScriptSimpleRunner;

public class ShellApp {

    public static void main(String[] args) {
        Command[] commands = { new Cat(), new WC(), new Manual(), new Pwd() };
        new Shell(new ShellParser(), new ShellScriptSimpleRunner(),
                  new MultiThreadCommandRunner(3), commands).start();
    }
}
