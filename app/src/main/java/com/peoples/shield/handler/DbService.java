package com.peoples.shield.handler;

import android.os.Handler;
import android.os.Looper;

import java.util.function.BiFunction;
import java.util.function.Consumer;

public class DbService {

    public <T> void execute(final DbOperation op, final T entity) {
        final EntityHandler<T> handler = DaoRegistry.getHandler((Class<T>) entity.getClass());

        DbExecutor.execute(() -> {
            switch (op) {
                case INSERT:
                    handler.insert(entity);
                    break;
                case UPDATE:
                    handler.update(entity);
                    break;
                case DELETE:
                    handler.delete(entity);
                    break;
            }
        });
    }

    public <T, R> void queryAsync(final Class<T> entityClass, final BiFunction<EntityHandler<T>, T, R> queryFn, final T param, final Consumer<R> callbackOnMain) {
        final EntityHandler<T> handler = DaoRegistry.getHandler(entityClass);
        DbExecutor.execute(() -> {
            final R result = queryFn.apply(handler, param);
            new Handler(Looper.getMainLooper()).post(() -> {
                if (callbackOnMain != null) callbackOnMain.accept(result);
            });
        });
    }
}
