package ru.job4j.pool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {

    public static Sums[] sum(int[][] matrix) {
        Sums[] sumsArray = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sumsArray[i] = getSums(matrix, i);
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
        return CompletableFuture.supplyAsync(() -> getSums(matrix, i));
    }

    private static Sums getSums(int[][] matrix, int i) {
        int rowSum = 0;
        int colSum = 0;
        for (int j = 0; j < matrix.length; j++) {
            rowSum += matrix[i][j];
            colSum += matrix[j][i];
        }
        return new Sums(rowSum, colSum);
    }
}
