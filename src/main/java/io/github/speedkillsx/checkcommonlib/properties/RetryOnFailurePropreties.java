package io.github.speedkillsx.checkcommonlib.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for handling retry mechanisms in case of failures.
 * This class is used to configure retry-related settings based on the property
 * prefixed with "check-common.retry".
 *
 * The retry feature can be toggled using "enabled". The maximum number of
 * retry attempts and delay interval between retries can be set using
 * "defaultMaxAttempts" and "defaultDelayMs" respectively.
 *
 * These properties can be configured in the application's properties file to
 * customize retry behavior globally within the application.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "check-common.retry")

public class RetryOnFailurePropreties {
    private boolean enabled;
    private int defaultMaxAttempts;
    private long defaultDelayMs;
}
