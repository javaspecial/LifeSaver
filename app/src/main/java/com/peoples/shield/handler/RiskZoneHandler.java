package com.peoples.shield.handler;

import com.peoples.shield.dao.RiskZoneDao;
import com.peoples.shield.entity.RiskZone;

import java.util.List;

public class RiskZoneHandler implements EntityHandler<RiskZone> {
    private final RiskZoneDao dao;
    public RiskZoneHandler(RiskZoneDao dao) {
        this.dao = dao;
    }

    @Override
    public void insert(RiskZone entity) {
        dao.insert(entity);
    }

    @Override
    public void update(RiskZone entity) {
        dao.update(entity);
    }

    @Override
    public void delete(RiskZone entity) {
        dao.delete(entity);
    }

    @Override
    public RiskZone getOne() {
        return dao.getOne();
    }

    @Override
    public RiskZone getOneById(Long id) {
        return dao.getOneById(id);
    }

    @Override
    public List<RiskZone> getAll() {
        return dao.getAll();
    }
}
