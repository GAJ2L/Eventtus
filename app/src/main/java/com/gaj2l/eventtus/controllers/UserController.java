package com.gaj2l.eventtus.controllers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.gaj2l.eventtus.lib.Controller;
import com.gaj2l.eventtus.models.User;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class UserController extends Controller<User> {

    protected UserController(SQLiteDatabase database) {
        super(database, "user");
    }

    @Override
    protected ContentValues entityToValues(User entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", entity.getId());
        contentValues.put("name", entity.getName());
        contentValues.put("mail", entity.getMail());
        contentValues.put("method_autentication", entity.getMethodAutentication());

        return contentValues;
    }
}