package com.tonorganisation.checkcommonlib.aspect;

import com.tonorganisation.checkcommonlib.annotation.RetryOnFailure;
import com.tonorganisation.checkcommonlib.properties.RetryOnFailurePropreties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class RetryOnFailureAspect {
    private final RetryOnFailurePropreties retryOnFailurePropreties;

    public RetryOnFailureAspect(RetryOnFailurePropreties retryOnFailurePropreties) {
        this.retryOnFailurePropreties = retryOnFailurePropreties;
    }

    @Around("@annotation(retryAnnotation)")
    public Object around(ProceedingJoinPoint joinPoint, RetryOnFailure retryAnnotation) throws Throwable {
        int maxAttempts = retryAnnotation.maxAttempts();
        int tempAttempts = maxAttempts;
        long delayMs = retryAnnotation.delayMs();
        Class<? extends Throwable>[] includes = retryAnnotation.includes();
        Class<? extends Throwable>[] excludes = retryAnnotation.excludes();
        boolean shouldRetry;
        if (!retryOnFailurePropreties.isEnabled()){
            log.info("[RetryOnFailureAspect] Retry On Failure is disabled");
            return joinPoint.proceed();
        }
        log.info("[RetryOnFailureAspect] Retry On Failure for attempt {}", maxAttempts);
        while (maxAttempts > 0) {
            try {
                return joinPoint.proceed();
            } catch (Throwable capturedException) {
                log.info("[RetryOnFailureAspect] captured Exception = {} ", capturedException.getMessage());

                shouldRetry = shouldRetry(excludes, includes, capturedException);
                if (shouldRetry) {
                    log.info("[RetryOnFailureAspect] Attempting to proceed...");
                    maxAttempts--;
                    log.info("[RetryOnFailureAspect] Attempts Remaining = {}", maxAttempts);
                    Thread.sleep(delayMs);
                } else {
                    log.info("[RetryOnFailureAspect] Exception caught in retry point is considered as an excluded exception : {} ", capturedException.getClass());
                    break;
                }


            }


        }
        log.info("[RetryOnFailureAspect] Exceeded maximum attempts : {} for method {}", maxAttempts, joinPoint.getSignature().getName());
        return null;
    }

    private boolean shouldRetry(Class<? extends Throwable>[] excludes, Class<? extends Throwable>[] includes, Throwable exception) {
        for (Class<? extends Throwable> exclude : excludes) {
            if (exclude == exception.getClass()) {
                return false;
            }
        }

        for (Class<? extends Throwable> include : includes) {
            if (include == exception.getClass()) {
                return true;
            }
        }
        return false;
    }


}
