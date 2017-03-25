package com.gaj2l.eventtus.controllers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.gaj2l.eventtus.lib.Controller;
import com.gaj2l.eventtus.models.Evaluation;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class EvaluationController extends Controller<Evaluation> {

    protected EvaluationController(SQLiteDatabase database) {
        super(database, "evaluation");
    }

    @Override
    protected ContentValues entityToValues(Evaluation entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", entity.getId());
        contentValues.put("comment", entity.getComment());
        contentValues.put("starts", entity.getStars());

        putDate(contentValues, "dt_send", entity.getDtSend());
        putDate(contentValues, "dt_store", entity.getDtStore());

        return contentValues;
    }
}