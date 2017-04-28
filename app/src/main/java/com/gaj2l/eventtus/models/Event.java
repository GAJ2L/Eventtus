package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.lib.Entity;

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Event extends Entity {
    private String name;
    private String description;
    private String banner;
    private OffsetDateTime dtStart;
    private OffsetDateTime dtEnd;
    private String contactName;
    private String contactPhone;
    private String contactMail;

    public Event() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
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

    public String getRangeTime()
    {
        String time = "";
        OffsetDateTime start = this.getDtStart();
        OffsetDateTime end   = this.getDtEnd();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        if(start.format(formatter).equalsIgnoreCase(end.format(formatter)) )
        {
            time = start.format(formatter);
        }
        else
        {
            time = start.format(formatter) + " - " + end.format(formatter);
        }

        return time;
    }

    public String getRangeDate()
    {
        String date = "";
        OffsetDateTime start = this.getDtStart();
        OffsetDateTime end   = this.getDtEnd();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-y");
        if(start.format(formatter).equalsIgnoreCase(end.format(formatter)) )
        {
            date = start.format(formatter);
        }
        else
        {
            date = start.format(formatter) + " / " + end.format(formatter);
        }

        return date;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
