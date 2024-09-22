package com.logger;

import java.util.ArrayList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private ArrayList<String> logs;

    public Logger() {
        this.logs = new ArrayList<String>();
    }

    public void log(String message) {
        // Get the current date and time
        LocalDateTime now = LocalDateTime.now();
        
        // Define a formatter for the desired output format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format the current date and time as a string
        String formattedDateTime = now.format(formatter);
        this.logs.add("[" + formattedDateTime + "] " + message);
    }

    public void printLogs() {
        for (String log : this.logs) {
            System.out.println(log);
        }
    }

    public ArrayList<String> getLogs() {
        return this.logs;
    }

    
}
