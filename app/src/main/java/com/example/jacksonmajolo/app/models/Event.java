package com.example.jacksonmajolo.app.models;

import com.example.jacksonmajolo.app.lib.Entity;

import java.sql.Timestamp;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public class Event extends Entity {
    private String name;
    private String description;
    private String banner;
    private Timestamp dt_start;
    private Timestamp dt_end;
    private String contact_name;
    private String contact_phone;
    private String contact_mail;

    public Event() {}

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBanner() {
        return this.banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Timestamp getDt_start() {
        return this.dt_start;
    }

    public void setDt_start(Timestamp dt_start) {
        this.dt_start = dt_start;
    }

    public Timestamp getDt_end() {
        return this.dt_end;
    }

    public void setDt_end(Timestamp dt_end) {
        this.dt_end = dt_end;
    }

    public String getContact_phone() {
        return this.contact_phone;
    }

    public void setContact_phone(String contact_fone) {
        this.contact_phone = contact_fone;
    }

    public String getContact_name() {
        return this.contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_mail() {
        return this.contact_mail;
    }

    public void setContact_mail(String contact_mail) {
        this.contact_mail = contact_mail;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
