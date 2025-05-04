package io.github.speedkillsx.checkcommonlib.config;

import io.github.speedkillsx.checkcommonlib.properties.AuditProperties;
import io.github.speedkillsx.checkcommonlib.properties.LogExecutionTimeProperties;
import io.github.speedkillsx.checkcommonlib.properties.RetryOnFailurePropreties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * CheckCommonAutoConfiguration is a Spring configuration class that
 * auto-configures beans related to common functionalities such as
 * auditing, logging execution time, and retry mechanisms. These functionalities
 * are managed through properties prefixed with "check-common" and loaded from
 * the application's configuration file.
 *
 * It integrates configuration support for the following types of properties:
 * - {@link AuditProperties} for enabling or disabling auditing features.
 * - {@link LogExecutionTimeProperties} for enabling or disabling method execution time logging.
 * - {@link RetryOnFailurePropreties} for configuring retry mechanisms in case of failures.
 *
 * The class enables AspectJ auto-proxy support and ensures that the
 * necessary configuration beans are available for components relying on
 * these common features.
 */
@Configuration
@EnableConfigurationProperties({
        AuditProperties.class,
        LogExecutionTimeProperties.class,
        RetryOnFailurePropreties.class
})
@EnableAspectJAutoProxy
public class CheckCommonAutoConfiguration {
}
