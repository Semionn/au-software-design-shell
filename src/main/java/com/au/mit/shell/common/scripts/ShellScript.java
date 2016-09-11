package com.au.mit.shell.common.scripts;

import com.au.mit.shell.common.command.tasks.TaskDescription;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by semionn on 10.09.16.
 */
public class ShellScript {
    private List<TaskDescription> pipedTasks = new ArrayList<>();

    public ShellScript() {}

    public void addTask(TaskDescription task) {
        if (pipedTasks == null) {
            pipedTasks = new ArrayList<>();
        }
        pipedTasks.add(task);
    }

    public List<TaskDescription> getPipedTasks() {
        return pipedTasks;
    }

    public void setPipedTasks(List<TaskDescription> pipedTasks) {
        this.pipedTasks = pipedTasks;
    }
}
