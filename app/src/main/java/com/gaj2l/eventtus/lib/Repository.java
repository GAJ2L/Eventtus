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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public abstract class Repository<T extends Entity> {

    private final SQLiteDatabase database;
    private final String table;
    private final Class<T> entityClass;
    private int columnIndexId;

    protected Repository(Class<T> entity, SQLiteDatabase database, String table) {
        this.database = database;
        this.table = table;
        this.entityClass = entity;

        this.mapColumnsInternal();
    }

    private void insert(T entity) throws Exception {
        ContentValues contentValues = this.entityToValues(entity, true);
        this.database.insert(this.table, null, contentValues);
    }

    private void update(T entity) throws Exception {
        ContentValues contentValues = this.entityToValues(entity, false);
        String[] args = {String.valueOf(entity.getId())};

        this.database.update(this.table, contentValues, "_id=?", args);
    }

    public void store(T entity) throws Exception {
        try {
            this.database.beginTransaction();

            if (entity.isNew()) {
                insert(entity);
            } else {
                update(entity);
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            throw e;
        } finally {
            this.database.endTransaction();
        }
    }

    public void delete(T entity) throws Exception {
        String[] args = {String.valueOf(entity.getId())};
        this.database.delete(this.table, "_id=?", args);
    }

    public T get(int id) {
        String[] args = {String.valueOf(id)};

        Cursor cursor = this.database.query(this.table, null, "_id=?", args, null, null, null, null);

        T entity = this.cursorToEntity(cursor);

        cursor.close();

        return entity;
    }

    public List<T> list() {
        ArrayList<T> list = new ArrayList<>();

        Cursor cursor = this.database.query(this.table, null, null, null, null, null, null, null);

        while (cursor.moveToNext()){
            T entity = this.cursorToEntity(cursor);

            list.add(entity);
        }

        cursor.close();
        return list;
    }

    protected ContentValues entityToValues(T entity, boolean insert) {
        ContentValues contentValues = new ContentValues();
        if (insert) {
            contentValues.put("_id", entity.getId());
        }

        return contentValues;
    }

    protected T cursorToEntity(Cursor cursor) {
        try {
            T entity = this.entityClass.newInstance();
            entity.setId(cursor.getInt(this.columnIndexId));

            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void mapColumns(Cursor cursor) {
        this.columnIndexId = cursor.getColumnIndexOrThrow("_id");
    }

    private void mapColumnsInternal() {
        final String sql = "SELECT * FROM " + this.table + " LIMIT 1;";
        Cursor cursor = this.database.rawQuery(sql, null);
        this.mapColumns(cursor);
        cursor.close();
    }

    protected abstract void validate(T entity) throws Exception;

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
