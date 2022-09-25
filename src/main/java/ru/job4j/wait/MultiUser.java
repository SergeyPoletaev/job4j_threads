package ru.job4j.wait;

public class MultiUser {

    public static void main(String[] args) {
        Barrier barrier = new Barrier();
        Thread master = new Thread(() -> {
            barrier.on();
            System.out.println(Thread.currentThread().getName() + " started");
        }, "Master");
        Thread slave = new Thread(() -> {
            barrier.check();
            System.out.println(Thread.currentThread().getName() + " started");
        }, "Slave");
        master.start();
        slave.start();
    }
}
