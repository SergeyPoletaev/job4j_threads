package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String target;

    public Wget(String url, int speed, String target) {
        this.url = url;
        this.speed = speed;
        this.target = target;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream out = new FileOutputStream(target)) {
            byte[] dataBuffer = new byte[1024];
            int readBytes;
            int downloadData = 0;
            long start = System.nanoTime();
            while ((readBytes = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, readBytes);
                downloadData += readBytes;
                if (downloadData >= speed * 1_000_000) {
                    long time = System.nanoTime() - start;
                    if (time < 1_000_000_000) {
                        Thread.sleep(1000 - time / 1_000_000);
                    }
                    downloadData = 0;
                    start = System.nanoTime();
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("""
                    Для корректной работы программы передайте 2 параметра:
                    - url для скачивания файла;
                    - скорость скачивания в MB/s (Мбайт/с) в виде целого числаж
                    - путь до места сохранения и название файла.
                    например: "https://proof.ovh.net/files/10Mb.dat 1 ./10Mb.dat"
                    """);
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        String target = args[2];
        Thread wget = new Thread(new Wget(url, speed, target));
        wget.start();
        wget.join();
    }
}
