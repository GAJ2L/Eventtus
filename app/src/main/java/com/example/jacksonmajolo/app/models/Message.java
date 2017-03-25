package com.example.jacksonmajolo.app.models;

import java.sql.Timestamp;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Message {
    private String content;
    private Timestamp dt_store;
    private Timestamp dt_send;
    private User user;
    private Activity activity;

    public Message() {}

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDt_store() {
        return this.dt_store;
    }

    public void setDt_store(Timestamp dt_store) {
        this.dt_store = dt_store;
    }

    public Timestamp getDt_send() {
        return this.dt_send;
    }

    public void setDt_send(Timestamp dt_send) {
        this.dt_send = dt_send;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return this.content;
    }
}
