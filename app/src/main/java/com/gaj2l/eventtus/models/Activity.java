package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.lib.Entity;
import com.gaj2l.eventtus.lib.Util;

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Activity extends Entity {
    private String name;
    private OffsetDateTime dtStart;
    private OffsetDateTime dtEnd;
    private String localName;
    private String localGeolocation;
    private long eventId;
    private long evaluationId;

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

    public String getRangeTime() {
        String time = "";
        if (Util.getTimeFomatted(dtStart).equalsIgnoreCase(Util.getTimeFomatted(dtEnd))) {
            time = Util.getTimeFomatted(dtStart);
        } else {
            time = Util.getTimeFomatted(dtStart)+ " / " + Util.getTimeFomatted(dtEnd);
        }
        return time;
    }

    public String getRangeDate() {
        String date = "";
        if (Util.getDateFomatted(dtStart).equalsIgnoreCase(Util.getDateFomatted(dtEnd))) {
            date = Util.getDateFomatted(dtStart);
        } else {
            date = Util.getDateFomatted(dtStart) + " / " + Util.getDateFomatted(dtEnd);
        }
        return date;
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

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getEvaluationId() {
        return evaluationId;
    }

    public void setEvaluationId(long evaluationId) {
        this.evaluationId = evaluationId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
