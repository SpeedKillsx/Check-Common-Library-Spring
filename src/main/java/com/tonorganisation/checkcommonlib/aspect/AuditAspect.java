package com.tonorganisation.checkcommonlib.aspect;

import com.tonorganisation.checkcommonlib.properties.AuditProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AuditAspect {


    private final AuditProperties auditProperties;

    public AuditAspect(AuditProperties auditProperties) {
        this.auditProperties = auditProperties;
    }

    @Around("@annotation(com.tonorganisation.checkcommonlib.annotation.AuditAnnotation)")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
            if (!auditProperties.isAuditEnable()){
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
