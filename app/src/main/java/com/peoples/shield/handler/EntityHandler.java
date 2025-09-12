package com.peoples.shield.handler;

import java.util.List;

public interface EntityHandler<T> {
    void insert(T entity);
    void update(T entity);
    void delete(T entity);
    List<T> getAll();
    T getOne();
}
