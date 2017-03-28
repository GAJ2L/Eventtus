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
    private int eventId;
    private int evaluationId;

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

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(int evaluationId) {
        this.evaluationId = evaluationId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
