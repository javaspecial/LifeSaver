package com.peoples.shield.handler;

import java.util.List;

public interface SuperEntityHandler<T> {
    void insert(T entity);
    void update(T entity);
    void delete(T entity);
    List<T> getAll();
    List<T> getAllByIds(List idList);
    T getOne();
    T getOneById(Long id);
}
