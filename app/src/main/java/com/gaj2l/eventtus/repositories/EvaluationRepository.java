package com.gaj2l.eventtus.repositories;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.lib.Repository;
import com.gaj2l.eventtus.models.Evaluation;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class EvaluationRepository extends Repository<Evaluation> {
    public static final String COLUMN_COMMENT = "name";
    public static final String COLUMN_STARS = "local";
    public static final String COLUMN_DT_SEND = "activity_id";
    public static final String COLUMN_DT_STORE = "type_attachment_id";
    private int columnIndexComment;
    private int columnIndexStars;
    private int columnIndexDtSend;
    private int columnIndexDtStore;

    public EvaluationRepository(SQLiteDatabase database) {
        super(Evaluation.class, database, "evaluation");
    }

    @Override
    protected ContentValues entityToValues(Evaluation entity, boolean insert) {
        ContentValues contentValues = super.entityToValues(entity, insert);

        contentValues.put(COLUMN_COMMENT, entity.getComment());
        contentValues.put(COLUMN_STARS, entity.getStars());
        putDate(contentValues, COLUMN_DT_SEND, entity.getDtSend());
        putDate(contentValues, COLUMN_DT_STORE, entity.getDtStore());

        return contentValues;
    }

    @Override
    protected Evaluation cursorToEntity(Cursor cursor) {
        Evaluation entity = super.cursorToEntity(cursor);

        entity.setComment(cursor.getString(this.columnIndexComment));
        entity.setStars(cursor.getLong(this.columnIndexStars));
        entity.setDtSend(getDate(cursor, this.columnIndexDtSend));
        entity.setDtStore(getDate(cursor, this.columnIndexDtStore));

        return entity;
    }

    @Override
    protected void mapColumns(Cursor cursor) {
        super.mapColumns(cursor);

        this.columnIndexComment = cursor.getColumnIndex(COLUMN_COMMENT);
        this.columnIndexStars = cursor.getColumnIndex(COLUMN_STARS);
        this.columnIndexDtSend = cursor.getColumnIndex(COLUMN_DT_SEND);
        this.columnIndexDtStore = cursor.getColumnIndex(COLUMN_DT_STORE);
    }
}