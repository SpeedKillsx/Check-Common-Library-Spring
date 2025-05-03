package com.tonorganisation.checkcommonlib.aspect;

import com.tonorganisation.checkcommonlib.annotation.LightRetryAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LightRetryAspect {
    @Around("@annotation(com.tonorganisation.checkcommonlib.annotation.LightRetryAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        int maxAttempts = lightRetr

        return null;
    }




}
