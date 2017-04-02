package com.gaj2l.eventtus.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.models.TypeAttachment;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class TypeAttachmentRepository extends Repository<TypeAttachment> {
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_IMAGE = "image";
    private int columnIndexName;
    private int columnIndexImage;

    public TypeAttachmentRepository(SQLiteDatabase database) {
        super(TypeAttachment.class, database, "type_attachment");
    }

    @Override
    protected ContentValues entityToValues(TypeAttachment entity, boolean insert) {
        ContentValues contentValues = super.entityToValues(entity, insert);

        contentValues.put(COLUMN_NAME, entity.getName());
        contentValues.put(COLUMN_IMAGE, entity.getImage());

        return contentValues;
    }

    @Override
    protected TypeAttachment cursorToEntity(Cursor cursor) {
        TypeAttachment entity = super.cursorToEntity(cursor);

        entity.setName(cursor.getString(this.columnIndexName));
        entity.setImage(cursor.getString(this.columnIndexImage));

        return entity;
    }

    @Override
    protected void mapColumns(Cursor cursor) {
        super.mapColumns(cursor);

        this.columnIndexName = cursor.getColumnIndex(COLUMN_NAME);
        this.columnIndexImage = cursor.getColumnIndex(COLUMN_IMAGE);
    }
}