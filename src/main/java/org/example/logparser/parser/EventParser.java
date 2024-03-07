package org.example.logparser.parser;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EventParser {
    public static void main(String[] args) {
        String logFilePath = "C:/Users/SyrineFekih/Downloads/logs_20240304_152841/Logs/app.log";
        String targetLogger = "[IObeyaOsgiContainerManagerImpl]";

        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (isValidLogEntry(line, targetLogger)) {
                    LogEntry logEntry = parseLogEntry(line);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    JSONObject jsonLogEntry = new JSONObject();
                    jsonLogEntry.put("DateAndTime", sdf.format(logEntry.timestamp));
                    jsonLogEntry.put("LogLevel", logEntry.logLevel);
                    jsonLogEntry.put("JobLogger",logEntry.logger);
                    jsonLogEntry.put("Message",logEntry.message);
                    System.out.println(jsonLogEntry.toString(3));

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidLogEntry(String logEntry, String targetLogger) {
        String[] parts = logEntry.split(" ", 6);

        if (parts.length < 6) {
            return false; // Invalid log entry
        }

        String logger = parts[4];
        return logger.equals(targetLogger);
    }

    private static LogEntry parseLogEntry(String logEntry) {
        String[] parts = logEntry.split(" ", 6);

        if (parts.length < 6) {
            return null; // Invalid log entry
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTime = sdf.parse(parts[0] + " " + parts[1]);
            String logLevel = parts[3];
            String logger = parts[4];
            String message = parts[5];

            return new LogEntry(dateTime, logLevel, logger, message);
        } catch (ParseException e) {
            System.err.println("Error parsing log entry: " + logEntry);
            e.printStackTrace();
            return null;
        }
    }

    private static class LogEntry {
        Date timestamp;
        String logLevel;
        String logger;
        String message;

        LogEntry(Date timestamp, String logLevel, String logger, String message) {
            this.timestamp = timestamp;
            this.logLevel = logLevel;
            this.logger = logger;
            this.message = message;
        }
    }
}

