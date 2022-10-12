package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static class Sums {
        private final int rowSum;
        private final int colSum;

        public Sums(int rowSum, int colSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Sums sums = (Sums) o;
            return rowSum == sums.rowSum && colSum == sums.colSum;
        }

        @Override
        public int hashCode() {
            return Objects.hash(rowSum, colSum);
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sumsArray = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            sumsArray[i] = new Sums(rowSum, colSum);
        }
        return sumsArray;
    }

    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Map<Integer, CompletableFuture<Sums>> future = new HashMap<>();
        Sums[] sumsArray = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            future.put(i, getTask(matrix, i));
        }
        for (Integer key : future.keySet()) {
            sumsArray[key] = future.get(key).get();
        }
        return sumsArray;
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int i) {
        return CompletableFuture.supplyAsync(() -> {
            int rowSum = 0;
            int colSum = 0;
            for (int j = 0; j < matrix.length; j++) {
                rowSum += matrix[i][j];
                colSum += matrix[j][i];
            }
            return new Sums(rowSum, colSum);
        });
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        int length = 10_000;
        int[][] arr = new int[length][length];
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < length; j++) {
                arr[i][j] = count++;
            }
        }
        long start1 = System.currentTimeMillis();
        sum(arr);
        System.out.println("sync " + (System.currentTimeMillis() - start1) + " mc");
        long start2 = System.currentTimeMillis();
        asyncSum(arr);
        System.out.println("async " + (System.currentTimeMillis() - start2) + " mc");
    }
}
