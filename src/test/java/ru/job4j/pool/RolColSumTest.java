package ru.job4j.pool;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;

public class RolColSumTest {

    @Test
    public void syncSum() {
        int[][] arr = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] exp = {
                new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(24, 18)
        };
        assertThat(RolColSum.sum(arr)).isEqualTo(exp);
    }

    @Test
    public void asyncSum() throws ExecutionException, InterruptedException {
        int[][] arr = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        RolColSum.Sums[] exp = {
                new RolColSum.Sums(6, 12),
                new RolColSum.Sums(15, 15),
                new RolColSum.Sums(24, 18)
        };
        assertThat(RolColSum.asyncSum(arr)).isEqualTo(exp);
    }
}