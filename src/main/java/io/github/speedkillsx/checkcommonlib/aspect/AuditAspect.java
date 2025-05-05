package io.github.speedkillsx.checkcommonlib.aspect;

import io.github.speedkillsx.checkcommonlib.properties.AuditProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * AuditAspect provides an aspect for auditing method calls in REST controllers.
 * It uses the @Around advice to intercept method execution of classes annotated
 * with @RestController. The aspect checks if auditing is enabled in the
 * application properties (via AuditProperties) and logs details such as the
 * method signature and returned result.
 * <p>
 * This allows developers to track and monitor method calls in REST endpoints,
 * providing insights into the behavior of the application.
 * <p>
 * Key Features:
 * - Enables or disables auditing based on a configuration property.
 * - Logs method signatures and return values for observed methods.
 * - Provides a mechanism to capture and analyze audit-related information.
 * <p>
 * Dependencies:
 * - Requires AuditProperties to determine if auditing is enabled.
 * - Utilizes Spring AOP annotations such as @Aspect and @Around.
 * <p>
 * Note:
 * Auditing can be toggled globally using the "check-common.audit-enable" property
 * in the application configuration file.
 */
@Slf4j
@Aspect
public class AuditAspect {

    private final AuditProperties auditProperties;

    /**
     * Constructs an AuditAspect instance with the provided AuditProperties.
     *
     * @param auditProperties configuration object representing auditing properties
     *                        for the application. It determines if auditing is
     *                        globally enabled or disabled in the application
     *                        through the "check-common.audit-enable" property.
     */
    public AuditAspect(AuditProperties auditProperties) {
        this.auditProperties = auditProperties;
    }

    /**
     * Audit Aspect
     *
     * @param joinPoint : Point join
     * @return : Object
     * @throws Throwable : Throw an exception or error
     */
    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!auditProperties.isAuditEnable()) {
            log.info("[AUDIT] AuditEnable is not enabled");
            return joinPoint.proceed();
        }
        log.info("[AUDIT] Starting the Audit...");
        Object result = joinPoint.proceed();
        log.info("[AUDIT] Method signature: {}", joinPoint.getSignature());
        log.info("[AUDIT] Method name: {} ; Returned result {} ", joinPoint.getSignature().getName(), result);

        return result;

    }
}
