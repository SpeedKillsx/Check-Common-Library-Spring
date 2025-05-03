package com.tonorganisation.checkcommonlib.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "check-common")
public class LogExecutionTimeProperties {
    private boolean logExecutionTime;
}
