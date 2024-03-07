package org.example.logparser.jmx;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource(objectName = "com.example:type=ErrorLog")
public class ErrorLog {
    private String date;
    private String JobLogger;
    private String ErrorLevel;
    private String Message;
    @ManagedAttribute
    public String getDate() {
        return date;
    }
    @ManagedAttribute
    public String getJobLogger() {
        return JobLogger;
    }

    @ManagedAttribute
    public String getErrorLevel() {
        return ErrorLevel;
    }

    @ManagedAttribute
    public String getMessage() {
        return Message;
    }

    @ManagedOperation
    public void setDate(String date) {
        this.date = date;
    }

    @ManagedOperation
    public void setJobLogger(String jobLogger) {
        JobLogger = jobLogger;
    }

    @ManagedOperation
    public void setErrorLevel(String errorLevel) {
        ErrorLevel = errorLevel;
    }

    @ManagedOperation
    public void setMessage(String message) {
        Message = message;
    }
}
