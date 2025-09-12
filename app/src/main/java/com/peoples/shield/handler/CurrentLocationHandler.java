package com.peoples.shield.handler;

import com.peoples.shield.dao.CurrentLocationDao;
import com.peoples.shield.entity.CurrentLocation;

import java.util.List;

public class CurrentLocationHandler implements EntityHandler<CurrentLocation> {
    private final CurrentLocationDao dao;
    public CurrentLocationHandler(CurrentLocationDao dao) {
        this.dao = dao;
    }

    @Override
    public void insert(CurrentLocation entity) {
        dao.insert(entity);
    }

    @Override
    public void update(CurrentLocation entity) {
        dao.update(entity);
    }

    @Override
    public void delete(CurrentLocation entity) {
        dao.delete(entity);
    }

    @Override
    public CurrentLocation getOne() {
        return dao.getOne();
    }

    @Override
    public List<CurrentLocation> getAll() {
        return dao.getAll();
    }
}
