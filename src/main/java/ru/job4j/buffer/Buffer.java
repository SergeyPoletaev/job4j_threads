package ru.job4j.buffer;

/**
 * объектом монитора является текущий объект класса Buffer
 * критическая секция - тело метода add и тело метода toString
 */
public class Buffer {
    private final StringBuilder buffer = new StringBuilder();

    public synchronized void add(int value) {
        System.out.print(value);
        buffer.append(value);
    }

    @Override
    public synchronized String toString() {
        return buffer.toString();
    }
}
