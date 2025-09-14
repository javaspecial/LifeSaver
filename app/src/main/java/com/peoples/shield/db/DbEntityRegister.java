package com.peoples.shield.db;

import com.peoples.shield.entity.CurrentLocation;
import com.peoples.shield.entity.RiskZone;
import com.peoples.shield.handler.BaseEntityHandler;
import com.peoples.shield.handler.CurrentLocationHandler;
import com.peoples.shield.handler.RiskZoneHandler;

import java.util.HashMap;
import java.util.Map;

public class DbEntityRegister {
    private static final Map<Class<?>, BaseEntityHandler<?>> registry = new HashMap<>();

    public static <T> void register(Class<T> entityClass, BaseEntityHandler<T> handler) {
        registry.put(entityClass, handler);
    }

    @SuppressWarnings("unchecked")
    public static <T> BaseEntityHandler<T> getHandler(Class<T> entityClass) {
        BaseEntityHandler<?> h = registry.get(entityClass);
        if (h == null) throw new IllegalStateException("No handler registered for " + entityClass);
        return (BaseEntityHandler<T>) h;
    }

    public static void doRegisterEntity(DbRoom db) {
        DbEntityRegister.register(CurrentLocation.class, new CurrentLocationHandler(db.currentLocationDao()));
        DbEntityRegister.register(RiskZone.class, new RiskZoneHandler(db.riskZoneDao()));
    }
}
