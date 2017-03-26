package com.gaj2l.eventtus.controllers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.lib.Controller;
import com.gaj2l.eventtus.models.PreferenceUser;
import com.gaj2l.eventtus.validations.PreferenceUserValidation;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class PreferenceUserController extends Controller<PreferenceUser> {

    protected PreferenceUserController(SQLiteDatabase database) {
        super(database, "preference_user");
    }

    @Override
    protected ContentValues entityToValues(PreferenceUser entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", entity.getId());
        contentValues.put("fl_notification", entity.isFlNotification());
        contentValues.put("user_id", entity.getUser().getId());

        return contentValues;
    }

     @Override
     protected void validate(PreferenceUser entity) throws Exception {
         PreferenceUserValidation preferenceUserValidation = new PreferenceUserValidation();
         preferenceUserValidation.validate(entity);
     }
 }