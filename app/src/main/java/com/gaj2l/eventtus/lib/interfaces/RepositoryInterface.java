package com.gaj2l.eventtus.lib.interfaces;

import com.gaj2l.eventtus.lib.Entity;

import java.util.List;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public interface RepositoryInterface<T extends Entity> {

    void store(T entity) throws Exception;

    void delete(T entity) throws Exception;

    T get(int id);

    List<T> list();
}
