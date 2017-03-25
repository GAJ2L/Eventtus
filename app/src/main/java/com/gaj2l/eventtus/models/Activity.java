package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.lib.Entity;

import java.sql.Timestamp;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Activity extends Entity{
    private String name;
    private Timestamp dt_start;
    private Timestamp dt_end;
    private String local_name;
    private String local_geolocation;
    private Event event;
    private Evaluation evaluation;

    public Activity() {}

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDt_start() {
        return this.dt_start;
    }

    public void setDt_start(Timestamp dt_start) { this.dt_start = dt_start;}

    public Timestamp getDt_end() {
        return this.dt_end;
    }

    public void setDt_end(Timestamp dt_end) {
        this.dt_end = dt_end;
    }

    public String getLocal_name() {
        return this.local_name;
    }

    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    public String getLocal_geolocation() {
        return this.local_geolocation;
    }

    public void setLocal_geolocation(String local_geolocation) { this.local_geolocation = local_geolocation; }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Evaluation getEvaluation() {
        return this.evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
