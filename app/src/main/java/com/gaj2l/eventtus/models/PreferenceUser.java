package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.lib.Entity;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class PreferenceUser extends Entity {
    private boolean fl_notification;
    private User user;

    public PreferenceUser() {}

    public boolean isFl_notification() {
        return this.fl_notification;
    }

    public void setFl_notification(boolean fl_notification) { this.fl_notification = fl_notification; }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.valueOf(this.fl_notification);
    }
}
