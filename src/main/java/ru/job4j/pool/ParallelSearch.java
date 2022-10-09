package ru.job4j.pool;

import java.util.Objects;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch<V> extends RecursiveTask<Integer> {
    private final V[] array;
    private final int from;
    private final int to;
    private final V value;

    public ParallelSearch(V[] array, V value, int from, int to) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.value = value;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return indexOf(array, value);
        }
        int mid = from + (to - from) / 2;
        ParallelSearch<V> searchLeft = new ParallelSearch<>(array, value, from, mid);
        ParallelSearch<V> searchRight = new ParallelSearch<>(array, value, mid + 1, to);
        searchLeft.fork();
        searchRight.fork();
        var left = searchLeft.join();
        var right = searchRight.join();
        return left != -1 ? left : right;
    }

    private int indexOf(V[] array, V value) {
        int rsl = -1;
        for (int i = from; i <= to; i++) {
            if (Objects.equals(array[i], value)) {
                rsl = i;
                break;
            }
        }
        return rsl;
    }

    public static <V> int search(V[] array, V value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> task = new ParallelSearch<>(array, value, 0, array.length - 1);
        return forkJoinPool.invoke(task);
    }
}
