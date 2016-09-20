package com.au.mit.shell.common;

import com.au.mit.shell.common.command.Argument;
import com.au.mit.shell.common.command.tasks.TaskDescription;
import com.au.mit.shell.common.scripts.ShellScript;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by semionn on 11.09.16.
 */
public class Environment {
    private Map<String, String> variables = new HashMap<>();

    public Environment() {
    }

    public void addVariable(String name, String value) {
        variables.put(name, value);
    }

    public void replaceVariables(ShellScript shellScript) {
        List<TaskDescription> pipedTasks = shellScript.getPipedTasks();
        for (TaskDescription taskDescr : pipedTasks) {
            taskDescr.setCommandName(replaceVariables(taskDescr.getCommandName()));
            taskDescr.setArgs(replaceVariables(taskDescr.getArgs()));
        }
        shellScript.setPipedTasks(pipedTasks);
    }
    private List<Argument> replaceVariables(List<Argument> strings) {
        for (int i = 0; i < strings.size(); i++) {
            String fixedName = replaceVariables(strings.get(i).getName());
            String fixedValue = replaceVariables(strings.get(i).getValue());
            strings.set(i, new Argument(fixedName, fixedValue));
        }
        return strings;
    }

    private String replaceVariables(String string) {
        for (Map.Entry<String, String> kv : variables.entrySet()) {
            string = string.replace("$" + kv.getKey(), kv.getValue());
        }
        return string;
    }
}
