package com.amayaslab.check_common_lib.aspect;
import com.amayaslab.check_common_lib.annotation.AuditAnnotation;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class AuditAspect {
    @Around("@annotation(AuditAnnotation)")
    public Object audit(ProceedingJoinPoint joinPoint) throws Throwable {
            long startTime = System.currentTimeMillis();
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;
            log.info("Method signature: {}", joinPoint.getSignature());
            log.info("Method name: {} ; Returned result {} ", joinPoint.getSignature().getName(), result);
            log.info("Audit execution time: {} ms", executionTime);

        return result;

    }
}
