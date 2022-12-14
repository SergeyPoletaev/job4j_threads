package ru.job4j.wait;

public class Barrier {
    private boolean flag = false;
    private final Object monitor = this;

    public void on() {
        synchronized (monitor) {
            flag = true;
            monitor.notifyAll();
        }
    }

    public void off() {
        synchronized (monitor) {
            flag = false;
            monitor.notifyAll();
        }
    }

    public void check() {
        while (!flag) {
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
