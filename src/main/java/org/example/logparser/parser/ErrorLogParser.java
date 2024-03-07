package org.example.logparser.parser;
import org.example.logparser.jmx.ErrorLog;
import org.json.JSONObject;
import org.springframework.jmx.export.annotation.ManagedResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@ManagedResource(objectName = "com.example:type=ErrorLogs")
public class ErrorLogParser {
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("C:/Users/SyrineFekih/Downloads/logs_20240301_134244/logs/error.log"))) {
            String line;
            StringBuilder logEntry = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.*")) {
                    if (logEntry.length() > 0) {
                        processLogEntry(logEntry.toString());
                        logEntry.setLength(0);
                    }
                }
                logEntry.append(line).append(System.lineSeparator());
            }

            if (logEntry.length() > 0) {
                processLogEntry(logEntry.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processLogEntry(String logEntry) {
        String[] parts = logEntry.split(" ", 5);

        if (parts.length < 5) {
            System.err.println("Invalid log entry: " + logEntry);
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTime = sdf.parse(parts[0] + " " + parts[1]);

            String errorLevel = parts[2];
            String message = parts[4];
            //String logger = parts[3];
            String logger = parts[3].substring(1, Math.min(parts[3].length()-1, 100));

            String shortMessage = message.substring(0, Math.min(message.length(), 100));

            ErrorLog errorLog = new ErrorLog();
            errorLog.setDate(sdf.format(dateTime));
            errorLog.setErrorLevel(errorLevel);
            errorLog.setJobLogger(logger);
            errorLog.setMessage(shortMessage);


            JSONObject jsonLogEntry = new JSONObject();
            jsonLogEntry.put("DateAndTime", sdf.format(dateTime));
            jsonLogEntry.put("ErrorLevel", errorLevel);
            jsonLogEntry.put("JobLogger",logger);
            jsonLogEntry.put("Message", shortMessage);
            System.out.println(jsonLogEntry.toString(3));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

