package ru.job4j.synch;

import org.junit.jupiter.api.Test;
import ru.job4j.wait.SimpleBlockingQueue;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class SingleLockListTest {

    @Test
    public void add() throws InterruptedException {
        SingleLockList<Integer> list = new SingleLockList<>(List.of(1, 2));
        Thread first = new Thread(() -> list.add(3));
        Thread second = new Thread(() -> list.add(4));
        first.start();
        second.start();
        first.join();
        second.join();
        Set<Integer> rsl = new TreeSet<>();
        list.iterator().forEachRemaining(rsl::add);
        assertThat(rsl).isEqualTo(Set.of(1, 2, 3, 4));
    }

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final List<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(
                        e -> {
                            try {
                                queue.offer(e);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                )
        );
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        try {
                            buffer.add(queue.poll());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer).isEqualTo(Arrays.asList(0, 1, 2, 3, 4));
    }
}