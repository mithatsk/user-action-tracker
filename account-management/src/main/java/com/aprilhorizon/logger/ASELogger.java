package com.aprilhorizon.logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ASELogger {
    private static final String FILE_PATH = "src/main/java/logs/actions.log";
    private final Path path = Paths.get(FILE_PATH);

    public ASELogger() {
        createLogFileIfNotExisting();
    }

    private void createLogFileIfNotExisting() {
        if (Files.notExists(path)) {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void info(String message) {
        try {
            File file = new File(FILE_PATH);
            FileWriter fileWriter = new FileWriter(FILE_PATH, true);
            String logMessage = (file.length() == 0) ? message : ("," + "\n" + message);
            fileWriter.write(logMessage);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String read() {
        StringBuilder result = new StringBuilder();
        try {
            FileReader fileReader = new FileReader(FILE_PATH);
            int c;
            while((c = fileReader.read()) != -1)
                result.append((char)c);
            fileReader.close();
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
