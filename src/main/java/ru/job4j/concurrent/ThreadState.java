package ru.job4j.concurrent;

public class ThreadState {

    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println("Name first thread is " + Thread.currentThread().getName())
        );
        Thread second = new Thread(
                () -> System.out.println("Name second thread is " + Thread.currentThread().getName())
        );
        System.out.println("State first thread is " + first.getState());
        System.out.println("State second thread is " + second.getState());
        first.start();
        second.start();
        while (first.getState() != Thread.State.TERMINATED || second.getState() != Thread.State.TERMINATED) {
            System.out.println("State first thread is " + first.getState());
            System.out.println("State second thread is " + second.getState());
        }
        System.out.println("State first thread after loop " + first.getState());
        System.out.println("State second thread after loop " + second.getState());
        System.out.println("The work is done");
    }
}
