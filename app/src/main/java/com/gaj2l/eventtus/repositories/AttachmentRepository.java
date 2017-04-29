package com.gaj2l.eventtus.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.models.Attachment;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class AttachmentRepository extends Repository<Attachment> {
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOCAL = "local";
    public static final String COLUMN_SIZE = "size";
    public static final String COLUMN_ACTIVITY_ID = "activity_id";
    public static final String COLUMN_TYPE = "type";
    private int columnIndexName;
    private int columnIndexLocal;
    private int columnIndexActivityId;
    private int columnIndexType;
    private int columnIndexSize;


    public AttachmentRepository(SQLiteDatabase database) {
        super(Attachment.class, database, "attachment");
    }

    @Override
    protected ContentValues entityToValues(Attachment entity, boolean insert) {
        ContentValues contentValues = super.entityToValues(entity, insert);

        contentValues.put(COLUMN_NAME, entity.getName());
        contentValues.put(COLUMN_LOCAL, entity.getLocal());
        contentValues.put(COLUMN_ACTIVITY_ID, entity.getActivityId());
        contentValues.put(COLUMN_TYPE, entity.getType());
        contentValues.put(COLUMN_SIZE, entity.getSize());

        return contentValues;
    }

    @Override
    protected Attachment cursorToEntity(Cursor cursor) {
        Attachment entity = super.cursorToEntity(cursor);

        entity.setName(cursor.getString(this.columnIndexName));
        entity.setLocal(cursor.getString(this.columnIndexLocal));
        entity.setActivityId(cursor.getInt(this.columnIndexActivityId));
        entity.setType(cursor.getInt(this.columnIndexType));
        entity.setSize(cursor.getString(this.columnIndexSize));

        return entity;
    }

    @Override
    protected void mapColumns(Cursor cursor) {
        super.mapColumns(cursor);

        this.columnIndexName = cursor.getColumnIndex(COLUMN_NAME);
        this.columnIndexLocal = cursor.getColumnIndex(COLUMN_LOCAL);
        this.columnIndexActivityId = cursor.getColumnIndex(COLUMN_ACTIVITY_ID);
        this.columnIndexType = cursor.getColumnIndex(COLUMN_TYPE);
        this.columnIndexSize = cursor.getColumnIndex(COLUMN_SIZE);
    }
}