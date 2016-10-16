package com.au.mit.shell.common.scripts;

import com.au.mit.shell.common.command.tasks.TaskDescription;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores description of piped commands with their arguments
 */
public class ShellScript {
    private List<TaskDescription> pipedTasks = new ArrayList<>();

    /**
     * Add TaskDescription to the script
     */
    public void addTask(TaskDescription task) {
        if (pipedTasks == null) {
            pipedTasks = new ArrayList<>();
        }
        pipedTasks.add(task);
    }

    /**
     * Returns list of piped TaskDescriptions
     */
    public List<TaskDescription> getPipedTasks() {
        return pipedTasks;
    }

    /**
     * Set up list of piped TaskDescriptions
     */
    public void setPipedTasks(List<TaskDescription> pipedTasks) {
        this.pipedTasks = pipedTasks;
    }
}
