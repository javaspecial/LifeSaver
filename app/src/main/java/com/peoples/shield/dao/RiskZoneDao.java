package com.peoples.shield.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.peoples.shield.entity.RiskZone;

import java.util.List;
@Dao
public interface RiskZoneDao {
    @Insert
    void insert(RiskZone riskZone);
    @Insert
    void insertAll(List<RiskZone> riskZones);
    @Update
    void update(RiskZone riskZone);
    @Update
    void updateAll(List<RiskZone> riskZones);
    @Delete
    void delete(RiskZone riskZone);
    @Delete
    void deleteAll(List<RiskZone> riskZones);
    @Query("DELETE FROM risk_zone")
    void deleteAll();
    @Query(("SELECT * FROM risk_zone"))
    List<RiskZone> getAll();
    @Query("SELECT * FROM risk_zone LIMIT 1")
    RiskZone getOne();
    @Query("SELECT * FROM risk_zone WHERE id = :id LIMIT 1")
    RiskZone getOneById(Long id);
}
