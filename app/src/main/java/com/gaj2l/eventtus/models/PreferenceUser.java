package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.lib.Entity;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class PreferenceUser extends Entity {
    private boolean flNotification;
    private User user;

    public PreferenceUser() {}

    public boolean isFlNotification() {
        return flNotification;
    }

    public void setFlNotification(boolean flNotification) {
        this.flNotification = flNotification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.valueOf(this.flNotification);
    }
}
