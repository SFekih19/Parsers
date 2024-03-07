package org.example.logparser.service;

import org.example.logparser.entity.WarnLog;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
@Service
public class WarnService {
        public JSONObject parseLog(String logFilePath, String targetLevel) {
            JSONObject result = new JSONObject();

            try (BufferedReader reader = new BufferedReader(new FileReader(logFilePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (isValidLogEntry(line, targetLevel)) {
                        WarnLog logEntry = parseLogEntry(line);
                        JSONObject jsonLogEntry = createJsonLogEntry(logEntry);
                        result.put(logEntry.getTimestamp().toString(), jsonLogEntry);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        private boolean isValidLogEntry(String logEntry, String targetLevel) {
            String[] parts = logEntry.split(" ", 6);
            if (parts.length < 6) {
                return false;
            }
            String level = parts[3];
            return level.equals(targetLevel);
        }

        private WarnLog parseLogEntry(String logEntry) {
            String[] parts = logEntry.split(" ", 6);
            if (parts.length < 6) {
                return null;
            }
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date dateTime = sdf.parse(parts[0] + " " + parts[1]);
                String logLevel = parts[3];
                String logger = parts[4];
                String message = parts[5];
                return new WarnLog(dateTime, logLevel, logger, message);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        private JSONObject createJsonLogEntry(WarnLog logEntry) {
            JSONObject jsonLogEntry = new JSONObject();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jsonLogEntry.put("DateAndTime", sdf.format(logEntry.getTimestamp()));
            jsonLogEntry.put("LogLevel", logEntry.getLogLevel());
            jsonLogEntry.put("JobLogger", logEntry.getLogger());
            jsonLogEntry.put("Message", logEntry.getMessage());
            return jsonLogEntry;
        }
}

