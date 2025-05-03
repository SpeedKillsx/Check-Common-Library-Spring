package com.tonorganisation.checkcommonlib.config;

import com.tonorganisation.checkcommonlib.properties.AuditProperties;
import com.tonorganisation.checkcommonlib.properties.LogExecutionTimeProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableConfigurationProperties(AuditProperties.class)
@EnableAspectJAutoProxy
public class CheckCommonAutoConfiguration {


    @Bean
    public AuditProperties auditProperties() {
        return new AuditProperties();
    }

    @Bean
    public LogExecutionTimeProperties logEcecutionTimeProperties() {
        return new LogExecutionTimeProperties();
    }
}
