package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.lib.Entity;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Evaluation extends Entity {
    private float stars;
    private String comment;
    private OffsetDateTime dtStore;
    private OffsetDateTime dtSend;
    private String email;
    private long activityId;

    public Evaluation() {
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public long getActivity() {
        return activityId;
    }

    public void setActivity(long activityId) {
        this.activityId = activityId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return this.comment;
    }
}
