package com.peoples.shield.handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DbExecutor {
    private static ExecutorService executor = Executors.newSingleThreadExecutor();
    public static void execute(Runnable runnable) {
        if (executor.isShutdown() || executor.isTerminated()) {
            executor = Executors.newSingleThreadExecutor();
        }
        executor.execute(runnable);
    }
    public static void shutdown() {
        executor.shutdown();
    }
}
