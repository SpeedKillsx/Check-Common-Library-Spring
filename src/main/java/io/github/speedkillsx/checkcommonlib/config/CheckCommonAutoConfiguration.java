package io.github.speedkillsx.checkcommonlib.config;

import io.github.speedkillsx.checkcommonlib.aspect.AuditAspect;
import io.github.speedkillsx.checkcommonlib.aspect.LogExecutionTimeAspect;
import io.github.speedkillsx.checkcommonlib.aspect.RetryOnFailureAspect;
import io.github.speedkillsx.checkcommonlib.properties.AuditProperties;
import io.github.speedkillsx.checkcommonlib.properties.LogExecutionTimeProperties;
import io.github.speedkillsx.checkcommonlib.properties.RetryOnFailurePropreties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import io.github.speedkillsx.checkcommonlib.annotation.RetryOnFailure;
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
    /**
     * Creates and provides an instance of AuditAspect, which is responsible for
     * intercepting method invocations in classes annotated with @RestController
     * and performing auditing operations based on application properties.
     *
     * @param auditProperties configuration object containing properties related
     *                        to auditing, such as enabling or disabling the audit
     *                        functionality globally via the "check-common.audit-enable"
     *                        configuration property.
     * @return an AuditAspect bean that enables auditing functionality for REST controller methods.
     */
    @Bean
    public AuditAspect auditAspect( AuditProperties auditProperties) {
        return new AuditAspect(auditProperties);
    }

    /**
     * Creates and provides a LogExecutionTimeAspect bean, which is responsible for logging
     * the execution time of methods in classes annotated with @RestController. The logging
     * behavior is configurable and can be enabled or disabled through the associated
     * LogExecutionTimeProperties.
     *
     * @param logExecutionTimeProperties configuration object representing properties related to
     *                                    method execution time logging, such as globally enabling
     *                                    or disabling this feature via the "check-common.logExecutionTime"
     *                                    property.
     * @return a LogExecutionTimeAspect bean that facilitates logging of method execution times.
     */
    @Bean
    public LogExecutionTimeAspect logExecutionTimeAspect( LogExecutionTimeProperties logExecutionTimeProperties) {
        return new LogExecutionTimeAspect( logExecutionTimeProperties);
    }

    /**
     * Creates and provides a RetryOnFailureAspect bean that handles retry logic
     * for methods annotated with {@link RetryOnFailure}. The retry functionality
     * is globally configurable through application properties defined in
     * {@link RetryOnFailurePropreties}.
     *
     * @param retryOnFailurePropreties configuration object containing properties related
     *                                  to retry functionality, such as whether retry behavior
     *                                  is enabled for annotated methods and default retry settings
     *                                  like maximum attempts and delay duration.
     * @return a RetryOnFailureAspect bean used to apply the retry logic to methods
     *         annotated with {@link RetryOnFailure}.
     */
    @Bean
    public RetryOnFailureAspect retryOnFailureAspect( RetryOnFailurePropreties retryOnFailurePropreties) {
        return new RetryOnFailureAspect( retryOnFailurePropreties);
    }
}
