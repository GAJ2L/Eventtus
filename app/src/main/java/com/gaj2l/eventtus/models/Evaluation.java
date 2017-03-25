package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.lib.Entity;

import org.threeten.bp.OffsetDateTime;

import java.sql.Timestamp;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Evaluation extends Entity {
    private float stars;
    private String comment;
    private OffsetDateTime dtStore;
    private OffsetDateTime dtSend;

    public Evaluation() {
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
