package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.lib.Entity;
import com.gaj2l.eventtus.lib.Session;
import com.gaj2l.eventtus.lib.Util;

import org.json.JSONObject;
import org.threeten.bp.OffsetDateTime;

import java.io.Serializable;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Message extends Entity {
    private String content;
    private OffsetDateTime dtStore;
    private OffsetDateTime dtSend;
    private long userId;
    private long activityId;
    private long activityServiceId;
    private String email;

    public Message() {
    }

    public long getActivityServiceId() {
        return activityServiceId;
    }

    public void setActivityServiceId(long activityServiceId) {
        this.activityServiceId = activityServiceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getActivityId() {
        return activityId;
    }

    public void setActivityId(long activityId) {
        this.activityId = activityId;
    }

    @Override
    public String toString() {
        return this.content;
    }

    public String toJson()
    {
        try
        {
            JSONObject response = new JSONObject();
            response.put("email",email);
            response.put("date",Util.getAllDateFomatted(dtStore));
            response.put("activity",activityServiceId);
            response.put("content",content);

            return response.toString();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return "";
    }
}
