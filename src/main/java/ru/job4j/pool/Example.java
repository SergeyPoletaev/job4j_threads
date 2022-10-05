package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        pool.submit(() -> System.out.println("Exec " + Thread.currentThread().getName()));
        pool.submit(() -> System.out.println("Exec " + Thread.currentThread().getName()));
        pool.shutdown();
        while (!pool.isTerminated()) {
            Thread.sleep(100);
        }
        System.out.println("Exec " + Thread.currentThread().getName());
    }
}
