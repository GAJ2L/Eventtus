package com.gaj2l.eventtus.controllers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.lib.Controller;
import com.gaj2l.eventtus.models.Activity;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class ActivityController extends Controller<Activity> {

    protected ActivityController(SQLiteDatabase database) {
        super(database, "activity");
    }

    @Override
    protected ContentValues entityToValues(Activity entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", entity.getId());
        contentValues.put("name", entity.getName());
        contentValues.put("local_name", entity.getLocalName());
        contentValues.put("local_geolocation", entity.getLocalGeolocation());
        contentValues.put("event_id", entity.getEvent().getId());
        contentValues.put("evaluation_id", entity.getEvaluation().getId());

        putDate(contentValues, "dt_start", entity.getDtStart());
        putDate(contentValues, "dt_end", entity.getDtEnd());

        return contentValues;
    }
}
