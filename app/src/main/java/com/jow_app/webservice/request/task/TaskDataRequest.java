package com.jow_app.webservice.request.task;

import java.util.List;

/**
 * Created by diego on 4/2/17.
 */

public class TaskDataRequest {
    private String name;
    private long workspace;
    private long assignee;
    private String due_on;
    private long[] projects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getWorkspace() {
        return workspace;
    }

    public void setWorkspace(long workspace) {
        this.workspace = workspace;
    }

    public long getAssignee() {
        return assignee;
    }

    public void setAssignee(long assignee) {
        this.assignee = assignee;
    }

    public String getDue_on() {
        return due_on;
    }

    public void setDue_on(String due_on) {
        this.due_on = due_on;
    }

    public long[] getProjects() {
        return projects;
    }

    public void setProjects(long[] projects) {
        this.projects = projects;
    }

    public TaskDataRequest(String name, long workspace, long assignee, String due_on, long[] projects) {
        this.name = name;
        this.workspace = workspace;
        this.assignee = assignee;
        this.due_on = due_on;
        this.projects = projects;
    }
}
