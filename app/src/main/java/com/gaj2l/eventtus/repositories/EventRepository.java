package com.gaj2l.eventtus.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.models.Event;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class EventRepository extends Repository<Event> {
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_BANNER = "banner";
    public static final String COLUMN_CONTACT_MAIL = "contact_mail";
    public static final String COLUMN_CONTACT_PHONE = "contact_phone";
    public static final String COLUMN_CONTACT_NAME = "contact_name";
    public static final String COLUMN_DT_SEND = "dt_send";
    public static final String COLUMN_DT_STORE = "dt_store";
    private int columnIndexName;
    private int columnIndexDescription;
    private int columnIndexBanner;
    private int columnIndexContactMail;
    private int columnIndexContactPhone;
    private int columnIndexContactName;
    private int columnIndexDtSend;
    private int columnIndexDtStore;

    protected EventRepository(SQLiteDatabase database) {
        super(Event.class, database, "event");
    }

    @Override
    protected ContentValues entityToValues(Event entity, boolean insert) {
        ContentValues contentValues = super.entityToValues(entity, insert);

        contentValues.put(COLUMN_NAME, entity.getName());
        contentValues.put(COLUMN_DESCRIPTION, entity.getDescription());
        contentValues.put(COLUMN_BANNER, entity.getBanner());
        contentValues.put(COLUMN_CONTACT_MAIL, entity.getContactMail());
        contentValues.put(COLUMN_CONTACT_PHONE, entity.getContactPhone());
        contentValues.put(COLUMN_CONTACT_NAME, entity.getContactName());
        putDate(contentValues, COLUMN_DT_SEND, entity.getDtStart());
        putDate(contentValues, COLUMN_DT_STORE, entity.getDtEnd());

        return contentValues;
    }

    @Override
    protected Event cursorToEntity(Cursor cursor) {
        Event entity = super.cursorToEntity(cursor);

        entity.setName(cursor.getString(this.columnIndexName));
        entity.setDescription(cursor.getString(this.columnIndexDescription));
        entity.setBanner(cursor.getString(this.columnIndexBanner));
        entity.setContactMail(cursor.getString(this.columnIndexContactMail));
        entity.setContactPhone(cursor.getString(this.columnIndexContactPhone));
        entity.setContactName(cursor.getString(this.columnIndexContactName));
        entity.setDtStart(getDate(cursor, this.columnIndexDtSend));
        entity.setDtEnd(getDate(cursor, this.columnIndexDtStore));

        return entity;
    }

    @Override
    protected void mapColumns(Cursor cursor) {
        super.mapColumns(cursor);

        this.columnIndexName = cursor.getColumnIndex(COLUMN_NAME);
        this.columnIndexDescription = cursor.getColumnIndex(COLUMN_DESCRIPTION);
        this.columnIndexBanner = cursor.getColumnIndex(COLUMN_BANNER);
        this.columnIndexContactMail = cursor.getColumnIndex(COLUMN_CONTACT_MAIL);
        this.columnIndexContactPhone = cursor.getColumnIndex(COLUMN_CONTACT_PHONE);
        this.columnIndexContactName = cursor.getColumnIndex(COLUMN_CONTACT_NAME);
        this.columnIndexDtSend = cursor.getColumnIndex(COLUMN_DT_SEND);
        this.columnIndexDtStore = cursor.getColumnIndex(COLUMN_DT_STORE);
    }
}