package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    private final char[] process = new char[]{'\\', '|', '/'};

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt();
    }

    @Override
    public void run() {
        try {
            int count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(500);
                System.out.print("\r load: " + process[count++]);
                if (count >= process.length) {
                    count = 0;
                }
            }
        } catch (InterruptedException ignored) {

        }
    }
}