package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ParallelSearchTest {

    @Test
    public void whenTheElement30IsFoundThenReturnsTheIndex2() {
        Integer[] arr = {10, 20, 30, 40, 50};
        int rsl = ParallelSearch.search(arr, 30);
        assertThat(rsl).isEqualTo(2);
    }

    @Test
    public void whenTheElement10IsFoundThenReturnsTheIndex0() {
        Integer[] arr = {10, 20, 30, 40, 50};
        int rsl = ParallelSearch.search(arr, 10);
        assertThat(rsl).isEqualTo(0);
    }

    @Test
    public void whenTheElement50IsFoundThenReturnsTheIndex4() {
        Integer[] arr = {10, 20, 30, 40, 50};
        int rsl = ParallelSearch.search(arr, 50);
        assertThat(rsl).isEqualTo(4);
    }

    @Test
    public void whenTheElementNotFoundThenReturnsMinusOne() {
        Integer[] arr = {10, 20, 30, 40, 50};
        int rsl = ParallelSearch.search(arr, 60);
        assertThat(rsl).isEqualTo(-1);
    }
}