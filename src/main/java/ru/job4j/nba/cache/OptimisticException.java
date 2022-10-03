package ru.job4j.nba.cache;

public class OptimisticException extends RuntimeException {

    public OptimisticException(String msg) {
        super(msg);
    }
}
