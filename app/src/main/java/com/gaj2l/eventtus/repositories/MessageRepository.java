package com.gaj2l.eventtus.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.models.Message;
import com.gaj2l.eventtus.busines.validations.MessageValidation;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class MessageRepository extends Repository<Message> {
    public static final String COLUMN_CONTENT = "content";
    public static final String COLUMN_ACTIVITY_ID = "activity_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_DT_STORE = "dt_store";
    public static final String COLUMN_DT_SEND = "dt_send";
    private int columnIndexContent;
    private int columnIndexActivityId;
    private int columnIndexUserId;
    private int columnIndexDtStore;
    private int columnIndexDtSend;

    public MessageRepository(SQLiteDatabase database) {
        super(Message.class, database, "message");
    }

    @Override
    protected ContentValues entityToValues(Message entity, boolean insert) {
        ContentValues contentValues = super.entityToValues(entity, insert);

        contentValues.put(COLUMN_CONTENT, entity.getContent());
        contentValues.put(COLUMN_ACTIVITY_ID, entity.getActivityId());
        contentValues.put(COLUMN_USER_ID, entity.getUserId());
        putDate(contentValues, COLUMN_DT_SEND, entity.getDtSend());
        putDate(contentValues, COLUMN_DT_STORE, entity.getDtStore());

        return contentValues;
    }

    @Override
    protected Message cursorToEntity(Cursor cursor) {
        Message entity = super.cursorToEntity(cursor);

        entity.setContent(cursor.getString(this.columnIndexContent));
        entity.setActivityId(cursor.getInt(this.columnIndexActivityId));
        entity.setUserId(cursor.getInt(this.columnIndexUserId));
        entity.setDtSend(getDate(cursor, this.columnIndexDtSend));
        entity.setDtStore(getDate(cursor, this.columnIndexDtStore));

        return entity;
    }

    @Override
    protected void mapColumns(Cursor cursor) {
        super.mapColumns(cursor);

        this.columnIndexContent = cursor.getColumnIndex(COLUMN_CONTENT);
        this.columnIndexActivityId = cursor.getColumnIndex(COLUMN_ACTIVITY_ID);
        this.columnIndexUserId = cursor.getColumnIndex(COLUMN_USER_ID);
        this.columnIndexDtSend = cursor.getColumnIndex(COLUMN_DT_SEND);
        this.columnIndexDtStore = cursor.getColumnIndex(COLUMN_DT_STORE);
    }
}