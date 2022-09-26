package ru.job4j.wait;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleBlockingQueueTest {

    @Test
    void whenProducerOfferElementsThenConsumerPoolSame() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        List<Integer> rslList = new ArrayList<>();
        Thread producer = new Thread(() -> {
            queue.offer(1);
            queue.offer(3);
            queue.offer(2);
            queue.offer(4);
        });
        Thread consumer = new Thread(() -> {
            rslList.add(queue.poll());
            rslList.add(queue.poll());
            rslList.add(queue.poll());
            rslList.add(queue.poll());
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(rslList).isEqualTo(List.of(1, 3, 2, 4));
    }
}