package ru.job4j.wait;

public class ParallelSearch {

    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(5);
        int total = 3;
        final Thread consumer = new Thread(
                () -> {
                    int count = 0;
                    while (count != total) {
                        try {
                            System.out.println(queue.poll());
                            count++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Thread.currentThread().interrupt();
                        }
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {
                    int count = 0;
                    while (total != count) {
                        try {
                            queue.offer(count);
                            count++;
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).start();
    }
}