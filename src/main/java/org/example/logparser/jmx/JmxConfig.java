package org.example.logparser.jmx;

import org.example.logparser.jmx.ErrorLog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmxConfig {

    @Bean
    public ErrorLog ErrorLog() {
        return new ErrorLog();
    }
}

