package com.gaj2l.eventtus.controllers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.gaj2l.eventtus.lib.Controller;
import com.gaj2l.eventtus.models.Message;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class MessageController extends Controller<Message> {

    protected MessageController(SQLiteDatabase database) {
        super(database, "message");
    }

    @Override
    protected ContentValues entityToValues(Message entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", entity.getId());
        contentValues.put("content", entity.getContent());
        contentValues.put("activity_id", entity.getActivity().getId());
        contentValues.put("user_id", entity.getUser().getId());

        putDate(contentValues, "dt_store", entity.getDtStore());
        putDate(contentValues, "dt_send", entity.getDtSend());

        return contentValues;
    }
}