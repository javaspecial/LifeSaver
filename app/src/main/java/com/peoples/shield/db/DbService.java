package com.peoples.shield.db;

import android.os.Handler;
import android.os.Looper;

import com.peoples.shield.handler.BaseEntityHandler;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class DbService {

    public <T> void execute(final DbOperation op, final T entity) {
        final BaseEntityHandler<T> handler = DbEntityRegister.getHandler((Class<T>) entity.getClass());
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

    public <T> void getOne(Class<T> clazz, Consumer<T> callback) {
        this.queryAsync(clazz, (handler, param) -> handler.getOne(), null, callback);
    }

    public <T> void getOneById(Class<T> clazz, Consumer<T> callback, Long id) {
        this.queryAsync(clazz, (handler, param) -> handler.getOneById(id), null, callback);
    }

    public <T> void getAll(Class<T> clazz, Consumer<List<T>> callback) {
        this.queryAsync(clazz, (handler, param) -> handler.getAll(), null, callback);
    }

    public <T> void getAllByIds(Class<T> clazz, Consumer<List<T>> callback, List idList) {
        this.queryAsync(clazz, (handler, param) -> handler.getAllByIds(idList), null, callback);
    }

    public <T, R> void queryAsync(final Class<T> entityClass, final BiFunction<BaseEntityHandler<T>, T, R> queryFn, final T param, final Consumer<R> callbackOnMain) {
        final BaseEntityHandler<T> handler = DbEntityRegister.getHandler(entityClass);
        DbExecutor.execute(() -> {
            final R result = queryFn.apply(handler, param);
            new Handler(Looper.getMainLooper()).post(() -> {
                if (callbackOnMain != null) callbackOnMain.accept(result);
            });
        });
    }
}
