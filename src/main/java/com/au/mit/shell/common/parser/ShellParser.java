package com.au.mit.shell.common.parser;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.scripts.ShellScript;
import com.au.mit.shell.common.scripts.TaskDescription;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by semionn on 10.09.16.
 */
public class ShellParser {
    public ShellParser() {
    }

    public ShellScript parse(String string) {
        List<Lexem> lexems = getLexems(string);

        ShellScript script = new ShellScript();
        String taskStr = "";
        for (Lexem lexem : lexems) {
            if (!lexem.isString) {
                if (lexem.value.equals("|")) {
                    script.addTask(parseTask(taskStr));
                    taskStr = "";
                } else {
                    taskStr += lexem.value;
                }
            }
        }
        if (!taskStr.equals("")) {
            script.addTask(parseTask(taskStr));
        }
        return script;
    }

    private TaskDescription parseTask(String taskStr) {
        String[] tokens = taskStr.trim().split(" ");
        List<Argument> arguments = new ArrayList<>();
        for (int i = 1; i < tokens.length; i++) {
            arguments.add(new Argument("", tokens[i]));
        }
        return new TaskDescription(tokens[0], arguments);
    }

    private List<Lexem> getLexems(String string) {
        String[] quotedStrings = string.split("'");
        assert (quotedStrings.length % 2 == 1) : "Quotation(\') is opened, but don't closed";
        List<Lexem> lexems = new ArrayList<>();
        for (int i = 0; i < quotedStrings.length; i++) {
            boolean isQuoted = i % 2 != 0;
            if (!isQuoted) {
                String[] doubleQuotedStrings = quotedStrings[i].split("'");
                assert (doubleQuotedStrings.length % 2 == 1) : "Quotation(\") is opened, but don't closed";
                for (int j = 0; j < doubleQuotedStrings.length; j++) {
                    boolean isDoubleQuoted = j % 2 != 0;
                    String s = doubleQuotedStrings[j];
                    if (isDoubleQuoted) {
                        s = "\"" + s + "\"";
                    }
                    lexems.add(new Lexem(s, isDoubleQuoted));
                }
            } else {
                lexems.add(new Lexem("\'" + quotedStrings[i] + "\'", true));
            }
        }
        for (int i = lexems.size() - 1; i >= 0; i--) {
            if (!lexems.get(i).isString) {
                String[] pipedLexems = lexems.get(i).value.split("\\|");
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
        return lexems;
    }

    private static class Lexem {
        String value;
        boolean isString;

        Lexem(String value, boolean isString) {
            this.value = value;
            this.isString = isString;
        }
    }
}
