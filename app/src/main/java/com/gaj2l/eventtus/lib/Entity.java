package com.gaj2l.eventtus.lib;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Entity {
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == 0;
    }
}
