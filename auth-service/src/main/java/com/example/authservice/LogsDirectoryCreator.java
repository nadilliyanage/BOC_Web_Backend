package com.example.authservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class LogsDirectoryCreator implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        File logsDir = new File("logs");
        if (!logsDir.exists()) {
            logsDir.mkdir();
        }
    }
}