package com.peoples.shield.handler;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseEntityHandler<T> implements SuperEntityHandler<T> {
    @Override
    public void insert(T entity) { /* default no-op */ }

    @Override
    public void update(T entity) { /* default no-op */ }

    @Override
    public void delete(T entity) { /* default no-op */ }

    @Override
    public List<T> getAll() { return new ArrayList<>(); }

    @Override
    public List<T> getAllByIds(List idList) { return new ArrayList<>(); };

    @Override
    public T getOne() { return null; }

    @Override
    public T getOneById(Long id) { return null; }
}
