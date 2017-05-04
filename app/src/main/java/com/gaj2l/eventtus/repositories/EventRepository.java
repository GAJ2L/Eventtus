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
    public static final String COLUMN_DT_START = "dt_start";
    public static final String COLUMN_DT_END = "dt_end";
    public static final String COLUMN_DT_STORE = "dt_store";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_EVENT_SERVICE_ID = "event_service_id";
    private int columnIndexName;
    private int columnIndexDescription;
    private int columnIndexBanner;
    private int columnIndexContactMail;
    private int columnIndexContactPhone;
    private int columnIndexContactName;
    private int columnIndexDtStart;
    private int columnIndexDtEnd;
    private int columnIndexDtStore;
    private int columnIndexUserId;
    private int columnIndexEventServiceId;

    public EventRepository(SQLiteDatabase database) {
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
        putDate(contentValues, COLUMN_DT_START, entity.getDtStart());
        putDate(contentValues, COLUMN_DT_END, entity.getDtEnd());
        putDate(contentValues, COLUMN_DT_STORE, entity.getDtStore());
        contentValues.put(COLUMN_USER_ID, entity.getUserId());
        contentValues.put(COLUMN_EVENT_SERVICE_ID, entity.getEventServiceId());

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
        entity.setDtEnd(getDate(cursor, this.columnIndexDtEnd));
        entity.setDtStart(getDate(cursor, this.columnIndexDtStart));
        entity.setDtStore(getDate(cursor, this.columnIndexDtStore));
        entity.setId(cursor.getInt(this.columnIndexUserId));
        entity.setId(cursor.getInt(this.columnIndexEventServiceId));

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
        this.columnIndexDtStart = cursor.getColumnIndex(COLUMN_DT_START);
        this.columnIndexDtEnd = cursor.getColumnIndex(COLUMN_DT_END);
        this.columnIndexDtStore = cursor.getColumnIndex(COLUMN_DT_STORE);
        this.columnIndexUserId = cursor.getColumnIndex(COLUMN_USER_ID);
        this.columnIndexEventServiceId = cursor.getColumnIndex(COLUMN_EVENT_SERVICE_ID);
    }
}