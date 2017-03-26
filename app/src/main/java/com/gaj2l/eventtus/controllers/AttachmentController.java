package com.gaj2l.eventtus.controllers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.gaj2l.eventtus.lib.Controller;
import com.gaj2l.eventtus.models.Attachment;
import com.gaj2l.eventtus.validations.AttachmentValidation;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public class AttachmentController extends Controller<Attachment> {

    protected AttachmentController(SQLiteDatabase database) {
        super(database, "attachment");
    }

    @Override
    protected ContentValues entityToValues(Attachment entity) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", entity.getId());
        contentValues.put("name", entity.getName());
        contentValues.put("local", entity.getLocal());
        contentValues.put("activity_id", entity.getActivity().getId());
        contentValues.put("type_attachment_id", entity.getTypeAttachment().getId());

        return contentValues;
    }

    @Override
    protected void validate(Attachment entity) throws Exception {
        AttachmentValidation attachmentValidation = new AttachmentValidation();
        attachmentValidation.validate(entity);
    }
}