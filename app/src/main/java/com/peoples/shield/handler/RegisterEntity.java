package com.peoples.shield.handler;

import com.peoples.shield.entity.CurrentLocation;
import com.peoples.shield.entity.RiskZone;
import com.peoples.shield.room.AppDatabase;

public class RegisterEntity {
    public static void doRegisterEntity(AppDatabase db) {
        DaoRegistry.register(CurrentLocation.class, new CurrentLocationHandler(db.currentLocationDao()));
        DaoRegistry.register(RiskZone.class, new RiskZoneHandler(db.riskZoneDao()));
    }
}
