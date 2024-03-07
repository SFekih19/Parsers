package org.example.logparser.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WarnLogsConsumer {
    @KafkaListener(topics = "warn-logs", groupId = "my-group")
    public void consumeWarnLog(String logMessage) {
        // Process the log message (e.g., save to database, log to console)
        System.out.println("Received WARN log: " + logMessage);
    }
}
