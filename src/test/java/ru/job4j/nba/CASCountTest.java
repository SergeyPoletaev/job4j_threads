package ru.job4j.nba;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CASCountTest {

    @Test
    public void whenTwoThreadsIncrementThenCounterIsEqualToTheSumOfTheIncrements() throws InterruptedException {
        CASCount count = new CASCount();
        Thread thread1 = new Thread(
                () -> {
                    for (int i = 0; i < 52; i++) {
                        count.increment();
                    }
                }
        );
        Thread thread2 = new Thread(
                () -> {
                    for (int i = 0; i < 36; i++) {
                        count.increment();
                    }
                }
        );
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        assertThat(count.get()).isEqualTo(88);
    }
}