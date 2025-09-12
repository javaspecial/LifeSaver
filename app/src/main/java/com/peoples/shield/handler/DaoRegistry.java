package com.peoples.shield.handler;

import java.util.HashMap;
import java.util.Map;

public class DaoRegistry {
    private static final Map<Class<?>, EntityHandler<?>> registry = new HashMap<>();

    public static <T> void register(Class<T> entityClass, EntityHandler<T> handler) {
        registry.put(entityClass, handler);
    }

    @SuppressWarnings("unchecked")
    public static <T> EntityHandler<T> getHandler(Class<T> entityClass) {
        EntityHandler<?> h = registry.get(entityClass);
        if (h == null) throw new IllegalStateException("No handler registered for " + entityClass);
        return (EntityHandler<T>) h;
    }
}
