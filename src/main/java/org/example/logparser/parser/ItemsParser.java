package org.example.logparser.parser;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class ItemsParser {
    public static void main(String[] args) {
        String logFilePath = "C:/Users/SyrineFekih/Downloads/logs_20240301_134244/logs/app.log";
        int svgDuration = 0;
        int addElementDuration = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
            String line;
            StringBuilder logEntry = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                if (line.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.*")) {
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
        String[] parts = logEntry.split(" ", 6);

        if (parts.length < 6) {
            System.err.println("Invalid log entry: " + logEntry);
            return;
        }

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateTime = sdf.parse(parts[0] + " " + parts[1]);
            String errorLevel = parts[3];
            String logger = parts[4];
            String message = parts[5];

            Pattern durationPattern = Pattern.compile("(svgDuration|addElementsDuration|SVG in )=(\\d+)");
            Matcher durationMatcher = durationPattern.matcher(message);
            int addElementDuration = 0;
            int svgDuration = 0;
            while (durationMatcher.find()) {
                String durationType = durationMatcher.group(1);
                int durationValue = Integer.parseInt(durationMatcher.group(2));

                if ("svgDuration".equals(durationType)) {
                    svgDuration = durationValue;
                } else if ("addElementsDuration".equals(durationType)) {
                    addElementDuration = durationValue;
                }
            }

            JSONObject jsonLogEntry = new JSONObject();
            jsonLogEntry.put("DateAndTime", sdf.format(dateTime));
            jsonLogEntry.put("LogLevel", errorLevel);
            jsonLogEntry.put("JobLogger",logger);
            jsonLogEntry.put("Message",message);

            if(svgDuration != 0){
                jsonLogEntry.put("svgDuration",svgDuration);
            }
            if(addElementDuration !=0){
                jsonLogEntry.put("addElementDuration",addElementDuration);
            }
            System.out.println(jsonLogEntry.toString(3));

        } catch (ParseException e) {
            System.err.println("Error parsing log entry: " + logEntry);
            e.printStackTrace();
        }
    }
}
