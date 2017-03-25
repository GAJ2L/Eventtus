package com.example.jacksonmajolo.app.models;

import com.example.jacksonmajolo.app.lib.Entity;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Attachment extends Entity {
    private String name;
    private String local;
    private Activity activity;
    private TypeAttachment typeAttachment;

    public Attachment() {}

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

    public Activity getActivity() {
        return this.activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public TypeAttachment getTypeAttachment() {
        return this.typeAttachment;
    }

    public void setTypeAttachment(TypeAttachment typeAttachment) { this.typeAttachment = typeAttachment; }

    @Override
    public String toString() {
        return this.name;
    }
}
