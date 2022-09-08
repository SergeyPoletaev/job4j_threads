package ru.job4j.io;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.function.Predicate;

public class ParseFile {
    private final File file;

    public ParseFile(File file) {
        this.file = file;
    }

    public String getContent(Predicate<Character> pred) throws IOException {
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            StringBuilder output = new StringBuilder();
            int data;
            while ((data = i.read()) != -1) {
                if (pred.test((char) data)) {
                    output.append((char) data);
                }
            }
            return output.toString();
        }
    }

    public String getContentWithoutUnicode() throws IOException {
        return getContent(p -> p < 0x80);
    }

    public String getContentWithUnicode() throws IOException {
        return getContent(p -> true);
    }
}
