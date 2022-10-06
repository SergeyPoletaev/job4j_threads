package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void emailTo(User user) {
        pool.submit(() -> {
            String email = user.email();
            String username = user.userName();
            String subject = String.format("subject = Notification %s to email %s.", username, email);
            String body = String.format("body = Add a new event to %s", username);
            send(subject, body, email);
        });
    }

    public void send(String subject, String body, String email) {

    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
