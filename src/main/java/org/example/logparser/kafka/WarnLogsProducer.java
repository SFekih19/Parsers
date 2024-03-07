package org.example.logparser.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WarnLogsProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public WarnLogsProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendWarnLog(String logMessage) {
        kafkaTemplate.send("warn-logs", logMessage);
    }
}
