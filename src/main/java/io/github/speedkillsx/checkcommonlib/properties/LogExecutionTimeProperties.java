package io.github.speedkillsx.checkcommonlib.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration properties for logging execution time of methods in the application.
 * This class is used to enable or disable the logging of method execution duration globally
 * based on the property prefixed with "check-common".
 *
 * The execution time logging feature can be toggled using the "logExecutionTime" field,
 * which can be configured in the application's properties file.
 *
 * This property is primarily utilized in aspects or components that handle performance
 * monitoring or debugging by tracking method execution times.
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "check-common")
public class LogExecutionTimeProperties {
    private boolean logExecutionTime;
}
