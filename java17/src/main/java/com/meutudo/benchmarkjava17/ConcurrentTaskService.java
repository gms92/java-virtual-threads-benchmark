package com.meutudo.benchmarkjava17;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ConcurrentTaskService {

    public long runConcurrentTasks(int tasks, int delayMs) {
        long start = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(tasks);
        try {
            List<CompletableFuture<Void>> futures = new ArrayList<>(tasks);
            for (int i = 0; i < tasks; i++) {
                futures.add(CompletableFuture.runAsync(() -> {
                    try {
                        Thread.sleep(delayMs);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }, executor));
            }
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } finally {
            executor.shutdown();
        }

        return System.currentTimeMillis() - start;
    }
}
