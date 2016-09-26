package com.au.mit.shell.common.parser;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.scripts.ShellScript;
import com.au.mit.shell.common.command.tasks.TaskDescription;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by semionn on 10.09.16.
 */
public class ShellParser {
    public ShellParser() {
    }

    public ShellScript parse(String string) {
        List<Lexem> lexems = getLexems(string);

        ShellScript script = new ShellScript();
        List<String> taskStr = new ArrayList<>();
        for (Lexem lexem : lexems) {
            if (!lexem.isString) {
                if (lexem.getValue().equals("|")) {
                    script.addTask(parseTask(taskStr));
                    taskStr = new ArrayList<>();
                } else if (taskStr.contains("=")) {
                    script.addTask(parseTask(taskStr));
                    taskStr = new ArrayList<>();
                } else {
                    for (String s : lexem.getValues()) {
                        taskStr.add(s);
                    }
                }
            } else {
                for (String s : lexem.getValues()) {
                    taskStr.add(s);
                }
            }
        }
        if (!taskStr.isEmpty()) {
            script.addTask(parseTask(taskStr));
        }
        return script;
    }

    private TaskDescription parseTask(List<String> tokens) {
        List<Argument> arguments = new ArrayList<>();
        for (int i = 1; i < tokens.size(); i++) {
            arguments.add(new Argument("", tokens.get(i).trim()));
        }
        return new TaskDescription(tokens.get(0).trim(), arguments);
    }

    private List<Lexem> getLexems(String string) {
        String[] quotedStrings = string.split("'");
//        assert (quotedStrings.length % 2 == 1) : "Quotation(\') is opened, but don't closed";
        List<Lexem> lexems = new ArrayList<>();
        for (int i = 0; i < quotedStrings.length; i++) {
            boolean isQuoted = i % 2 != 0;
            if (!isQuoted) {
                String[] doubleQuotedStrings = quotedStrings[i].split("\"");
//                assert (doubleQuotedStrings.length % 2 == 1) : "Quotation(\") is opened, but don't closed";
                for (int j = 0; j < doubleQuotedStrings.length; j++) {
                    boolean isDoubleQuoted = j % 2 != 0;
                    String s = doubleQuotedStrings[j];
                    lexems.add(new Lexem(s, isDoubleQuoted));
                }
            } else {
                lexems.add(new Lexem(quotedStrings[i], true));
            }
        }
        for (int i = lexems.size() - 1; i >= 0; i--) {
            if (!lexems.get(i).isString) {
                String[] pipedLexems = lexems.get(i).getValue().split("\\|");
                if (pipedLexems.length > 1) {
                    lexems.remove(i);
                    int k = 0;
                    for (int j = 0; j < pipedLexems.length; j++) {
                        if (j > 0) {
                            lexems.add(i + k, new Lexem("|", false));
                            k++;
                        }
                        lexems.add(i + k, new Lexem(pipedLexems[j], false));
                        k++;
                    }
                }
            }
        }
        List<Lexem> lexemsResult = new ArrayList<>();
        Lexem spacedLexem = new Lexem();
        for (int i = 0; i < lexems.size(); i++) {
            String value = lexems.get(i).getValue();
            if (value.startsWith(" ")) {
                lexemsResult.add(spacedLexem);
                spacedLexem = new Lexem();
            }
            if (!lexems.get(i).isString) {
                String[] spacedLexems = value.trim().split(" ");
                for (int j = 0; j < spacedLexems.length; j++) {
                    if (j > 0) {
                        lexemsResult.add(spacedLexem);
                        spacedLexem = new Lexem();
                    }
                    spacedLexem.addSubLexem(extractAssignment(spacedLexems[j]));
                }
            } else {
                spacedLexem.addSubLexem(extractAssignment(value));
            }
            if (value.endsWith(" ")) {
                lexemsResult.add(spacedLexem);
                spacedLexem = new Lexem();
            }
        }
        if (!spacedLexem.isEmpty()) {
            lexemsResult.add(spacedLexem);
        }
        return lexemsResult;
    }

    private Lexem extractAssignment(String str) {
        Lexem lexem = new Lexem();
        String[] tokens = str.split("=");
        if (tokens.length == 0) {
            return new Lexem(str, true);
        }
        if (tokens[0].length() < str.length()) {
            lexem.addSubLexem("=");
            lexem.addSubLexem(tokens[0]);
            if (tokens[0].length() + 1 < str.length()) {
                lexem.addSubLexem(str.substring(tokens[0].length() + 1));
            }
        } else {
            lexem.addSubLexem(tokens[0]);
        }
        return lexem;
    }

    private static class Lexem {
        private List<String> values;
        private boolean isString;

        Lexem(String value, boolean isString) {
            this.values = new ArrayList<>();
            this.values.add(value);
            this.isString = isString;
        }

        Lexem() {
            this.values = new ArrayList<>();
            this.isString = false;
        }

        String getValue() {
            return values.get(0);
        }

        boolean isEmpty() {
            return values.size() == 0;
        }

        List<String> getValues() {
            return values;
        }

        void addSubLexem(String value) {
            values.add(value);
        }

        void addSubLexem(Lexem lexem) {
            for (String s : lexem.values) {
                addSubLexem(s);
            }
        }

    }
}
