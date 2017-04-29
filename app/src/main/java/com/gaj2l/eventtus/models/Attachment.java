package com.gaj2l.eventtus.models;

import com.gaj2l.eventtus.R;
import com.gaj2l.eventtus.lib.Entity;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Attachment extends Entity {
    private String name;
    private String size;
    private String local;
    private int activityId;
    private int type;

    public static int TYPE_IMAGE        = 0;
    public static int TYPE_PDF          = 1;
    public static int TYPE_LINK         = 2;
    public static int TYPE_PRESENTATION = 3;
    public static int TYPE_DOC          = 4;

    public static int TYPES_DRAWABLES[] = {R.drawable.image,R.drawable.pdf,R.drawable.link,R.drawable.slide,R.drawable.doc};

    public Attachment() {}

    public String getSize() { return size; }

    public void setSize(String size) { this.size = size; }

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

    public int getType() {
        return type;
    }

    public void setType(int type) { this.type = type; }

    @Override
    public String toString() {
        return this.name;
    }
}
