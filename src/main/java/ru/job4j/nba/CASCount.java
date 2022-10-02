package ru.job4j.nba;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int expVal;
        int newVal;
        do {
            expVal = count.get();
            newVal = expVal + 1;
        } while (!count.compareAndSet(expVal, newVal));
    }

    public int get() {
        return count.get();
    }
}
