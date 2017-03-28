package com.gaj2l.eventtus.lib;

import java.util.List;

/**
 * Created by Jackson Majolo on 27/03/2017.
 */

public abstract class Service<T extends Entity> {
    private final Repository<T> repository;
    private Validation<T> validation;

    protected Service(Repository<T> repository) {
        this.repository = repository;
    }

    public Repository<T> getRepository() {
        return repository;
    }

    public Validation<T> getValidation() {
        return validation;
    }

    public void setValidation(Validation<T> validation) {
        this.validation = validation;
    }

    public void store(T entity) throws Exception {
        if (this.validation != null) {
            this.validation.validate(entity);
        }
        this.repository.store(entity);
    }

    public void delete(T entity) throws Exception {
        this.repository.delete(entity);
    }

    public T get(int id) {
        return this.repository.get(id);
    }

    public List<T> list() {
        return this.repository.list();
    }
}
