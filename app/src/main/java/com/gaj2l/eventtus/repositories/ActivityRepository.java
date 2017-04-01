package com.gaj2l.eventtus.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.models.Activity;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class ActivityRepository extends Repository<Activity> {
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOCAL_NAME = "local_name";
    public static final String COLUMN_LOCAL_GEOLOCATION = "local_geolocation";
    public static final String COLUMN_EVENT_ID = "event_id";
    public static final String COLUMN_EVALUATION_ID = "evaluation_id";
    public static final String COLUMN_DT_START = "dt_start";
    public static final String COLUMN_DT_END = "dt_end";
    private int columnIndexName;
    private int columnIndexLocalName;
    private int columnIndexLocalGeolocation;
    private int columnIndexEventId;
    private int columnIndexEvaluationId;
    private int columnIndexDtStart;
    private int columnIndexDtEnd;

    public ActivityRepository(SQLiteDatabase database) {
        super(Activity.class, database, "activity");
    }

    @Override
    protected ContentValues entityToValues(Activity entity, boolean insert) {
        ContentValues contentValues = super.entityToValues(entity, insert);

        contentValues.put(COLUMN_NAME, entity.getName());
        contentValues.put(COLUMN_LOCAL_NAME, entity.getLocalName());
        contentValues.put(COLUMN_LOCAL_GEOLOCATION, entity.getLocalGeolocation());
        contentValues.put(COLUMN_EVENT_ID, entity.getEventId());
        contentValues.put(COLUMN_EVALUATION_ID, entity.getEvaluationId());
        putDate(contentValues, COLUMN_DT_START, entity.getDtStart());
        putDate(contentValues, COLUMN_DT_END, entity.getDtEnd());

        return contentValues;
    }

    @Override
    protected Activity cursorToEntity(Cursor cursor) {
        Activity entity = super.cursorToEntity(cursor);

        entity.setName(cursor.getString(this.columnIndexName));
        entity.setLocalName(cursor.getString(this.columnIndexLocalName));
        entity.setLocalGeolocation(cursor.getString(this.columnIndexLocalGeolocation));
        entity.setEventId(cursor.getInt(this.columnIndexEventId));
        entity.setEvaluationId(cursor.getInt(this.columnIndexEvaluationId));
        entity.setDtStart(getDate(cursor, this.columnIndexDtStart));
        entity.setDtEnd(getDate(cursor, this.columnIndexDtEnd));

        return entity;
    }

    @Override
    protected void mapColumns(Cursor cursor) {
        super.mapColumns(cursor);

        this.columnIndexName = cursor.getColumnIndex(COLUMN_NAME);
        this.columnIndexLocalName = cursor.getColumnIndex(COLUMN_LOCAL_NAME);
        this.columnIndexLocalGeolocation = cursor.getColumnIndex(COLUMN_LOCAL_GEOLOCATION);
        this.columnIndexEventId = cursor.getColumnIndex(COLUMN_EVENT_ID);
        this.columnIndexEvaluationId = cursor.getColumnIndex(COLUMN_EVALUATION_ID);
        this.columnIndexDtStart = cursor.getColumnIndex(COLUMN_DT_START);
        this.columnIndexDtEnd = cursor.getColumnIndex(COLUMN_DT_END);
    }
}
