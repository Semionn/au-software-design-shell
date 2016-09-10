package com.au.mit.shell.common.scripts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by semionn on 10.09.16.
 */
public class ShellScript {
    private List<TaskDescription> tasks = new ArrayList<>();

    public ShellScript() {}

    public void addTask(TaskDescription task) {
        if (tasks == null) {
            tasks = new ArrayList<>();
        }
        tasks.add(task);
    }

    public List<TaskDescription> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDescription> tasks) {
        this.tasks = tasks;
    }
}
