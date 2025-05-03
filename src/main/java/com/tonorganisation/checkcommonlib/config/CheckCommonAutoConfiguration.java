package com.tonorganisation.checkcommonlib.config;

import com.tonorganisation.checkcommonlib.properties.AuditProperties;
import com.tonorganisation.checkcommonlib.properties.LogExecutionTimeProperties;
import com.tonorganisation.checkcommonlib.properties.RetryOnFailurePropreties;
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
@EnableConfigurationProperties(AuditProperties.class)
@EnableAspectJAutoProxy
public class CheckCommonAutoConfiguration {

    /**
     * Creates and registers a bean of type {@link AuditProperties}.
     * This bean provides configuration properties prefixed with "check-common"
     * for auditing purposes, allowing the application to determine
     * whether auditing features are enabled or disabled.
     *
     * @return an instance of {@link AuditProperties} containing audit-related configuration properties
     */
    @Bean
    public AuditProperties auditProperties() {
        return new AuditProperties();
    }

    /**
     * Creates and registers a bean of type {@link LogExecutionTimeProperties}.
     * This bean provides configuration properties prefixed with "check-common"
     * for logging execution time within the application.
     *
     * @return an instance of {@link LogExecutionTimeProperties} containing execution time logging-related configuration properties
     */
    @Bean
    public LogExecutionTimeProperties logEcecutionTimeProperties() {
        return new LogExecutionTimeProperties();
    }

    /**
     * Creates and registers a bean of type {@link RetryOnFailurePropreties}.
     * This bean provides configuration properties prefixed with "check-common.retry"
     * for handling retry mechanisms in case of failures, allowing control over
     * enabling retries, maximum attempts, and delay between retries.
     *
     * @return an instance of {@link RetryOnFailurePropreties} containing retry-related configuration properties
     */
    @Bean
    public RetryOnFailurePropreties retryOnFailurePropreties() {
        return new RetryOnFailurePropreties();
    }
}
