package ru.job4j.io;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveFile {
    private final File file;

    public SaveFile(File file) {
        this.file = file;
    }

    public void saveContent(String content) throws IOException {
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.print(content);
        }
    }
}
