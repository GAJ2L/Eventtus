package com.gaj2l.eventtus.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.models.PreferenceUser;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class PreferenceUserRepository extends Repository<PreferenceUser> {
    public static final String COLUMN_FL_NOTIFICATION = "fl_notification";
    public static final String COLUMN_USER_ID = "user_id";
    private int columnIndexFlNotification;
    private int columnIndexUserId;

    public PreferenceUserRepository(SQLiteDatabase database) {
        super(PreferenceUser.class, database, "preference_user");
    }

    @Override
    protected ContentValues entityToValues(PreferenceUser entity, boolean insert) {
        ContentValues contentValues = super.entityToValues(entity, insert);

        contentValues.put(COLUMN_FL_NOTIFICATION, entity.isFlNotification());
        contentValues.put(COLUMN_USER_ID, entity.getUserId());

        return contentValues;
    }

    @Override
    protected PreferenceUser cursorToEntity(Cursor cursor) {
        PreferenceUser entity = super.cursorToEntity(cursor);

        //entity.setFlNotification(cursor.is(this.columnIndexContent));
        entity.setUserId(cursor.getInt(this.columnIndexUserId));

        return entity;
    }

    @Override
    protected void mapColumns(Cursor cursor) {
        super.mapColumns(cursor);

        this.columnIndexFlNotification = cursor.getColumnIndex(COLUMN_FL_NOTIFICATION);
        this.columnIndexUserId = cursor.getColumnIndex(COLUMN_USER_ID);
    }
}