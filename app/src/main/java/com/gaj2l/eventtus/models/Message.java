package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.lib.Entity;

import org.threeten.bp.OffsetDateTime;

import java.sql.Timestamp;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Message extends Entity {
    private String content;
    private OffsetDateTime dtStore;
    private OffsetDateTime dtSend;
    private User user;
    private Activity activity;

    public Message() {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public OffsetDateTime getDtStore() {
        return dtStore;
    }

    public void setDtStore(OffsetDateTime dtStore) {
        this.dtStore = dtStore;
    }

    public OffsetDateTime getDtSend() {
        return dtSend;
    }

    public void setDtSend(OffsetDateTime dtSend) {
        this.dtSend = dtSend;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public String toString() {
        return this.content;
    }
}
