package org.example.logparser.entity;

import java.util.Date;

public class WarnLog {
    Date timestamp;
    String logLevel;
    String logger;
    String message;

    public WarnLog(Date timestamp, String logLevel, String logger, String message) {
        this.timestamp = timestamp;
        this.logLevel = logLevel;
        this.logger = logger;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
