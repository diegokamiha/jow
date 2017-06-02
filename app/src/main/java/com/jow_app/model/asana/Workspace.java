package com.jow_app.model.asana;

/**
 * Created by diego on 4/9/17.
 */

public class Workspace {

    private long id;
    private String name;

    public Workspace(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
