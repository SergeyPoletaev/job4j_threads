package ru.job4j.nba.cache;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class Base {
    private final int id;
    private final AtomicInteger version;
    private String name;

    public Base(int id, AtomicInteger version) {
        this.id = id;
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public int getVersion() {
        return version.get();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void incrVersion() {
        this.version.incrementAndGet();
    }
}
