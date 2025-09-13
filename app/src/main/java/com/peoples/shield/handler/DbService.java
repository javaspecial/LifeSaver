package com.peoples.shield.handler;

import android.os.Handler;
import android.os.Looper;

import java.util.List;
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

    public <T> void getOneAsync(Class<T> clazz, Consumer<T> callback) {
        this.queryAsync(clazz, (handler, param) -> handler.getOne(), null, callback);
    }

    public <T> void getAllAsync(Class<T> clazz, Consumer<List<T>> callback) {
        this.queryAsync(clazz, (handler, param) -> handler.getAll(), null, callback);
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
