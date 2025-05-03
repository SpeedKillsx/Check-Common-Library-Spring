package com.tonorganisation.checkcommonlib.aspect;

import com.tonorganisation.checkcommonlib.properties.LogExecutionTimeProperties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogExecutionTimeAspect {
    private final LogExecutionTimeProperties logExecutionTimeProperties;

    public LogExecutionTimeAspect(LogExecutionTimeProperties logExecutionTimeProperties) {
        this.logExecutionTimeProperties = logExecutionTimeProperties;
    }
    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
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
