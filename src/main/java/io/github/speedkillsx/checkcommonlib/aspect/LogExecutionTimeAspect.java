package io.github.speedkillsx.checkcommonlib.aspect;

import io.github.speedkillsx.checkcommonlib.properties.LogExecutionTimeProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * LogExecutionTimeAspect is an aspect for logging the execution time of methods
 * within Spring controllers annotated with @RestController. It uses the @Around
 * advice to intercept method invocations and log timing-related information.
 *
 * The aspect works based on a configurable property (logExecutionTime) provided
 * by LogExecutionTimeProperties to enable or disable the execution time logging
 * globally for the application.
 *
 * Targets:
 * - Methods within classes annotated with @RestController.
 *
 * Features:
 * - Logs the start and end timestamps of method execution.
 * - Records and logs the total time elapsed during method execution.
 * - Includes the method name and result in the log output when logging is active.
 *
 * Implementation Details:
 * - The logging mechanism is conditionally enabled based on the value of
 *   logExecutionTimeProperties.isLogExecutionTime().
 * - Utilizes Spring AOP for method interception.
 *
 * Dependencies:
 * - Requires LogExecutionTimeProperties for determining if logging is active.
 * - Relies on Lombok's @Slf4j for logging functionality.
 *
 * Exceptions:
 * - Passes any exceptions thrown by the intercepted methods up to the caller.
 *
 * Constraints:
 * - Logging is effective only when logExecutionTime is set to true in the application
 *   configuration.
 */
@Slf4j
@Aspect
public class LogExecutionTimeAspect {
    private final LogExecutionTimeProperties logExecutionTimeProperties;

    /**
     * Constructs a LogExecutionTimeAspect instance with the provided LogExecutionTimeProperties.
     *
     * @param logExecutionTimeProperties configuration object representing execution time
     *                                    logging properties for the application. It determines
     *                                    if method execution time logging is enabled or disabled
     *                                    globally through the "check-common.logExecutionTime" property.
     */
    public LogExecutionTimeAspect(LogExecutionTimeProperties logExecutionTimeProperties) {
        this.logExecutionTimeProperties = logExecutionTimeProperties;
    }

    /**
     *
     * @param joinPoint : Joint point for Log execution time
     * @return : Object
     * @throws Throwable : Throw an exception, error
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object time(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!logExecutionTimeProperties.isLogExecutionTime()) {
            log.info("log execution time is set to false");
            return joinPoint.proceed();
        }
        log.info("[LogExecutionTime] Starting execution time recording for method {}...", joinPoint.getSignature().getName());
        Long startTime = System.currentTimeMillis();
        log.info("[LogExecutionTime] Starting execution time record : {} SECONDS", startTime / 1000.0);
        Object result = joinPoint.proceed();
        log.info("[LogExecutionTime] Result generated : {}", result);
        Long endTime = System.currentTimeMillis();
        log.info("[LogExecutionTime] Ending execution time record : {} SECONDS", endTime / 1000.0);
        double executionTime = (endTime - startTime) / 1000.0;
        log.info("[LogExecutionTime] Time elapsed during execution equals  : {} SECONDS", executionTime);
        return result;
    }
}
