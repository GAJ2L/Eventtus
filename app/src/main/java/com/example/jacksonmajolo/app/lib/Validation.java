package com.example.jacksonmajolo.app.lib;

/**
 * Created by Jackson Majolo on 24/03/2017.
 */

public abstract class Validation<T> {
    public abstract void validate(T entity) throws Exception;

}
