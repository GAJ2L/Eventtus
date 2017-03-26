package com.gaj2l.eventtus.controllers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.lib.Controller;
import com.gaj2l.eventtus.models.TypeAttachment;
import com.gaj2l.eventtus.validations.TypeAttachmentValidation;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class TypeAttachmentController extends Controller<TypeAttachment> {

    protected TypeAttachmentController(SQLiteDatabase database) {
        super(database, "type_attachment");
    }

    @Override
    protected ContentValues entityToValues(TypeAttachment entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", entity.getId());
        contentValues.put("name", entity.getName());
        contentValues.put("image", entity.getImage());

        return contentValues;
    }

    @Override
    protected void validate(TypeAttachment entity) throws Exception {
        TypeAttachmentValidation typeAttachmentValidation = new TypeAttachmentValidation();
        typeAttachmentValidation.validate(entity);
    }
}