package com.au.mit.shell.common.command.embedded;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.Command;
import com.au.mit.shell.common.command.PipelineUtils;
import com.au.mit.shell.common.exceptions.CommandException;
import org.apache.commons.cli.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.au.mit.shell.common.command.PipelineUtils.defaultCharset;

/**
 * Shell command for printing out lines selected by specified regular expression for piped input
 */
public class Grep implements Command {
    private static final Options options = new Options();
    static {
        Option i = new Option("i", "i", false, "case insensitive search");
        i.setRequired(false);
        options.addOption(i);

        Option w = new Option("w", "w", false, "search only full word");
        w.setRequired(false);
        options.addOption(w);

        Option a = new Option("A", "A", true, "print N lines after match");
        a.setRequired(false);
        options.addOption(a);
    }

    /**
     * Overridden Command method to print out matched lines with input as PipedInputStream
     * and list of arguments, and output as PipedOutputStream
     */
    @Override
    public void run(PipedInputStream inputStream, PipedOutputStream outputStream, List<Argument> args) {
        Arguments parsedArguments = parseOptions(args);
        BufferedOutputStream dataOutputStream = new BufferedOutputStream(outputStream);
        try {
            if (args.size()  == 0) {
                dataOutputStream.write("Not enough arguments".getBytes(defaultCharset()));
                dataOutputStream.flush();
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            try {
                String s;
                int passLinesCount = 0;
                while ((s = br.readLine()) != null) {
                    boolean matched = match(s, parsedArguments);
                    if (matched || passLinesCount > 0) {
                        passLinesCount--;
                        if (parsedArguments.hasOption("A") && matched) {
                            passLinesCount = Integer.parseInt(parsedArguments.getOption("A"));
                        }
                        s += PipelineUtils.getEndLine();
                        dataOutputStream.write(s.getBytes(defaultCharset()));
                    }
                }
            } finally {
                br.close();
            }
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
        return "grep";
    }

    private boolean match(String input, Arguments arguments) {
        String pattern = arguments.getPositionalArgumentValue(0);
        if (arguments.hasOption("w")) {
            pattern = "(\\s|^)" + pattern + "(\\s|$)";
        }
        Pattern p;
        if (arguments.hasOption("i")) {
            p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        } else {
            p = Pattern.compile(pattern);
        }
        Matcher m = p.matcher(input);
        return m.find();
    }

    private Arguments parseOptions(List<Argument> arguments) {
        String[] args = new String[arguments.size()];
        arguments.stream().map(Argument::getValue).collect(Collectors.toList()).toArray(args);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp(getName(), options);
            throw new CommandException("Fail parsing options for command", e);
        }
        List<Argument> cmdArguments = cmd.getArgList().stream()
                .map(s -> new Argument("", s))
                .collect(Collectors.toList());
        Map<String, String> cmdOptions = Arrays.stream(cmd.getOptions())
                .collect(Collectors.toMap(Option::getOpt, o -> o.getValue() == null ? "" : o.getValue()));

        return new Arguments(cmdArguments, cmdOptions);
    }

    private static class Arguments {
        List<Argument> positionalArguments;
        Map<String, String> options;

        public Arguments(List<Argument> positionalArguments, Map<String, String> options) {
            this.positionalArguments = positionalArguments;
            this.options = options;
        }

        public String getPositionalArgumentValue(int i) {
            return positionalArguments.get(i).getValue();
        }

        public String getOption(String optionName) {
            return options.get(optionName);
        }

        public boolean hasOption(String optionName) {
            return options.containsKey(optionName);
        }
    }
}
