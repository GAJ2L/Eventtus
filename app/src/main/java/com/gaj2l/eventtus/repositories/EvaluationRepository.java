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
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_STARS = "stars";
    public static final String COLUMN_DT_SEND = "dt_send";
    public static final String COLUMN_DT_STORE = "dt_store";
    public static final String COLUMN_REF_ACTIVITY = "activity_id";
    public static final String COLUMN_EMAIL = "email";
    private int columnIndexComment;
    private int columnIndexStars;
    private int columnIndexDtSend;
    private int columnIndexDtStore;
    private int columnIndexActivity;
    private int columnIndexEmail;

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
        contentValues.put(COLUMN_REF_ACTIVITY,entity.getActivity());
        contentValues.put(COLUMN_EMAIL,entity.getEmail());

        return contentValues;
    }

    @Override
    protected Evaluation cursorToEntity(Cursor cursor) {
        Evaluation entity = super.cursorToEntity(cursor);

        entity.setComment(cursor.getString(this.columnIndexComment));
        entity.setStars(cursor.getLong(this.columnIndexStars));
        entity.setDtSend(getDate(cursor, this.columnIndexDtSend));
        entity.setDtStore(getDate(cursor, this.columnIndexDtStore));
        entity.setEmail(cursor.getString(this.columnIndexEmail));
        entity.setActivity(cursor.getLong(this.columnIndexActivity));

        return entity;
    }

    @Override
    protected void mapColumns(Cursor cursor) {
        super.mapColumns(cursor);

        this.columnIndexComment = cursor.getColumnIndex(COLUMN_COMMENT);
        this.columnIndexStars = cursor.getColumnIndex(COLUMN_STARS);
        this.columnIndexDtSend = cursor.getColumnIndex(COLUMN_DT_SEND);
        this.columnIndexDtStore = cursor.getColumnIndex(COLUMN_DT_STORE);
        this.columnIndexActivity = cursor.getColumnIndex(COLUMN_REF_ACTIVITY);
        this.columnIndexEmail = cursor.getColumnIndex(COLUMN_EMAIL);
    }
}