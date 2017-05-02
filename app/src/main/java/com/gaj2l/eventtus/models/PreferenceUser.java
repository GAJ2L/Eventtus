package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.lib.Entity;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class PreferenceUser extends Entity {
    private boolean flNotification;
    private long userId;

    public PreferenceUser() {
    }

    public boolean isFlNotification() {
        return flNotification;
    }

    public void setFlNotification(boolean flNotification) {
        this.flNotification = flNotification;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return String.valueOf(this.flNotification);
    }
}
