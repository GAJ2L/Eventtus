package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.lib.Entity;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Attachment extends Entity {
    private String name;
    private String local;
    private int activityId;
    private int typeAttachmentId;

    public Attachment() {
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocal() {
        return this.local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public int getTypeAttachmentId() {
        return typeAttachmentId;
    }

    public void setTypeAttachmentId(int typeAttachmentId) {
        this.typeAttachmentId = typeAttachmentId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
