package com.gaj2l.eventtus.lib;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.threeten.bp.Instant;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZoneOffset;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.temporal.TemporalAccessor;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public abstract class Controller<T extends Entity> {

    private final SQLiteDatabase database;
    private final String table;

    protected Controller(SQLiteDatabase database, String table) {
        this.database = database;
        this.table = table;
    }

    public void insert(T entity) throws Exception{
        ContentValues contentValues = this.entityToValues(entity);

        this.database.insert(table, null, contentValues);
    }

    protected abstract ContentValues entityToValues(T entity);

    protected void putDate(ContentValues values, String columnName, OffsetDateTime data) {
        if (data == null) {
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        String value = data.atZoneSameInstant(ZoneOffset.UTC).format(formatter);
        values.put(columnName, value);
    }

    protected OffsetDateTime getDate(Cursor cursor, int columnIndex) {
        String value = cursor.getString(columnIndex);
        if (value == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        TemporalAccessor accessor = formatter.parse(value);
        Instant instant = Instant.from(accessor);

        return OffsetDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
