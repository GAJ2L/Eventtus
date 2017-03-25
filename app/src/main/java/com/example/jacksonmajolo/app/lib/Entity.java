package com.example.jacksonmajolo.app.lib;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Entity {
    private int id;

    public int get_id() { return id; }

    public void set_id(int id) {
        this.id = id;
    }

    public boolean isNew(){ return this.id == 0;}
}
