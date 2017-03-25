package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.lib.Entity;

import org.threeten.bp.OffsetDateTime;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Activity extends Entity {
    private String name;
    private OffsetDateTime dtStart;
    private OffsetDateTime dtEnd;
    private String localName;
    private String localGeolocation;
    private Event event;
    private Evaluation evaluation;

    public Activity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OffsetDateTime getDtStart() {
        return dtStart;
    }

    public void setDtStart(OffsetDateTime dtStart) {
        this.dtStart = dtStart;
    }

    public OffsetDateTime getDtEnd() {
        return dtEnd;
    }

    public void setDtEnd(OffsetDateTime dtEnd) {
        this.dtEnd = dtEnd;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getLocalGeolocation() {
        return localGeolocation;
    }

    public void setLocalGeolocation(String localGeolocation) {
        this.localGeolocation = localGeolocation;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
