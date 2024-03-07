package org.example.logparser.controller;

import org.example.logparser.kafka.WarnLogsProducer;
import org.example.logparser.service.WarnService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WarnController {
    private final WarnLogsProducer producer;
    private final WarnService warnService;

    public WarnController(WarnLogsProducer producer,WarnService warnService) {
        this.producer = producer;
        this.warnService=warnService;
    }

    @PostMapping("/publish-warn-log")
    public void publishWarnLog(@RequestBody String logMessage) {
        warnService.parseLog("C:/Users/SyrineFekih/Downloads/logs_20240304_152841/Logs/app.log","WARN");
        producer.sendWarnLog(logMessage);
    }

}
