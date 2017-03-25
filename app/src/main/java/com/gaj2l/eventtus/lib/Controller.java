package com.gaj2l.eventtus.lib;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson Majolo on 25/03/2017.
 */

public abstract class Controller<T extends Entity> {

    private Class<T> entityClass;
    private static String table;
    private SQLiteDatabase dataBase;

    public Controller(Class<T> entity) {
        this.table = entity.getSimpleName().toLowerCase();
        this.entityClass = entity;
    }

    public void store(T entity) throws Exception {

    }

    public void delete(int id) throws Exception {

    }

//    public T getObject(int id) throws Exception {
//
//    }
//
//    public List<T> getObjects() throws Exception {
//
//    }
}
