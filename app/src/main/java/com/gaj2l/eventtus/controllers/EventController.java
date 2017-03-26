package com.gaj2l.eventtus.controllers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.lib.Controller;
import com.gaj2l.eventtus.models.Event;
import com.gaj2l.eventtus.validations.EventValidation;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class EventController extends Controller<Event> {

    protected EventController(SQLiteDatabase database) {
        super(database, "event");
    }

    @Override
    protected ContentValues entityToValues(Event entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", entity.getId());
        contentValues.put("name", entity.getName());
        contentValues.put("description", entity.getDescription());
        contentValues.put("banner", entity.getBanner());
        contentValues.put("contact_mail", entity.getContactMail());
        contentValues.put("contact_phone", entity.getContactPhone());
        contentValues.put("contact_name", entity.getContactName());

        putDate(contentValues, "dt_start", entity.getDtStart());
        putDate(contentValues, "dt_end", entity.getDtEnd());

        return contentValues;
    }

    @Override
    protected void validate(Event entity) throws Exception {
        EventValidation eventValidation = new EventValidation();
        eventValidation.validate(entity);
    }
}