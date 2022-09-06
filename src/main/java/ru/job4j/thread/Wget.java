package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int readBytes;
            long start = System.nanoTime();
            while ((readBytes = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, readBytes);
                double loadingTimeInSec = (double) (System.nanoTime() - start) / 1_000_000_000;
                double factSpeed = 1024 / loadingTimeInSec;
                if (factSpeed > speed) {
                    Thread.sleep((long) ((factSpeed / speed) * loadingTimeInSec - loadingTimeInSec) * 1000);
                }
                start = System.nanoTime();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new IllegalArgumentException("""
                    Для корректной работы программы передайте 2 параметра:
                    - url для скачивания файла;
                    - скорость скачивания в виде целого числа.
                    """);
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}
