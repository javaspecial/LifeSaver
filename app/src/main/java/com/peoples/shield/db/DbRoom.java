package com.peoples.shield.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.peoples.shield.dao.CurrentLocationDao;
import com.peoples.shield.dao.RiskZoneDao;
import com.peoples.shield.entity.CurrentLocation;
import com.peoples.shield.entity.RiskZone;

@Database(entities = {CurrentLocation.class, RiskZone.class}, version = 1, exportSchema = false)
public abstract class DbRoom extends RoomDatabase {
    private static volatile DbRoom instance;
    public abstract RiskZoneDao riskZoneDao();
    public abstract CurrentLocationDao currentLocationDao();
    public static synchronized DbRoom getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), DbRoom.class, "risk_zone_db").fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
