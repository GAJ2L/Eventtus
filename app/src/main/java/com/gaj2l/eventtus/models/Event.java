package com.gaj2l.eventtus.models;

import android.graphics.Color;
import android.support.annotation.NonNull;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.lib.Entity;
import com.gaj2l.eventtus.lib.Util;

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Event
    extends
        Entity
            implements
                Comparable<Event>

{
    private String name;
    private String description;
    private String banner;
    private OffsetDateTime dtStart;
    private OffsetDateTime dtEnd;
    private OffsetDateTime dtStore;
    private String contactName;
    private String contactPhone;
    private String contactMail;
    private String cor;
    private String logo;
    private long userId;
    private long eventServiceId;

    public static int STATE_IDLE     = 0;
    public static int STATE_FINISHED = 1;
    public static int STATE_PROGRESS = 2;

    public  static int STATE_DRAWABLES[] = {R.drawable.idle,R.drawable.finished,R.drawable.runnig};
    public  static int STATE_TITLE[]     = {R.string.idle,R.string.completed,R.string.in_progress};
    public  static int STATE_COLORS[]    = {R.color.color_idle,R.color.color_finished,R.color.color_running};

    public Event() {
    }

    public int getState()
    {
        if(OffsetDateTime.now().isAfter(getDtEnd()))
        {
            return STATE_FINISHED;
        }
        else if(OffsetDateTime.now().isAfter(getDtStart()))
        {
            return STATE_PROGRESS;
        }
        else
        {
            return STATE_IDLE;
        }
    }

    public long getEventServiceId() {
        return eventServiceId;
    }

    public void setEventServiceId(long eventServiceId) {
        this.eventServiceId = eventServiceId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCor() {return cor;}

    public void setCor(String cor) {this.cor = cor;}

    public String getLogo() {return logo;}

    public void setLogo(String logo) {this.logo = logo;}

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

    public OffsetDateTime getDtStore() {
        return dtStore;
    }

    public void setDtStore(OffsetDateTime dtStore) {
        this.dtStore = dtStore;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(@NonNull Event event) {
        return getDtStart().compareTo( event.getDtStart() );
    }
}
