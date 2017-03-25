package com.example.jacksonmajolo.app.models;

import java.sql.Timestamp;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Evaluation {
    private double stars;
    private String comment;
    private Timestamp dt_store;
    private Timestamp dt_send;

    public Evaluation() {}

    public double getStars() {
        return this.stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getDt_send() {
        return this.dt_send;
    }

    public void setDt_send(Timestamp dt_send) {
        this.dt_send = dt_send;
    }

    public Timestamp getDt_store() {
        return this.dt_store;
    }

    public void setDt_store(Timestamp dt_store) {
        this.dt_store = dt_store;
    }

    @Override
    public String toString() {
        return this.comment;
    }
}
