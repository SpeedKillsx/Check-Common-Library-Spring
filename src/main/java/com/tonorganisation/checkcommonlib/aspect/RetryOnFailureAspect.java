package com.tonorganisation.checkcommonlib.aspect;

import com.tonorganisation.checkcommonlib.annotation.RetryOnFailure;
import com.tonorganisation.checkcommonlib.properties.RetryOnFailurePropreties;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Aspect implementation that handles retrying logic for methods annotated with {@link RetryOnFailure}.
 * The retry mechanism utilizes configuration properties and annotation attributes to determine
 * maximum retry attempts, delay between attempts, and exceptions to include or exclude in the retry cycle.
 *
 * This aspect enables integration of retry logic without requiring the methods themselves to include
 * explicit retry-handling code. Retry logic is globally controlled via {@link RetryOnFailurePropreties},
 * while specific method-level settings can be applied using the {@link RetryOnFailure} annotation.
 *
 * The retry functionality checks whether it is enabled via application-defined properties. If disabled,
 * the annotated methods execute with no retry logic applied. Retry cycles are managed based on the
 * maximum attempts and delay specified, and exceptions are evaluated against include and exclude lists.
 */
@Slf4j
@Aspect
@Component
public class RetryOnFailureAspect {
    private final RetryOnFailurePropreties retryOnFailurePropreties;

    /**
     * Constructs a RetryOnFailureAspect instance with the provided RetryOnFailurePropreties.
     *
     * @param retryOnFailurePropreties configuration object representing retry-on-failure
     *                                 properties for the application. It defines global
     *                                 retry settings such as whether retry logic is
     *                                 enabled for methods annotated with @RetryOnFailure.
     */
    public RetryOnFailureAspect(RetryOnFailurePropreties retryOnFailurePropreties) {
        this.retryOnFailurePropreties = retryOnFailurePropreties;
    }

    /**
     *
     * @param joinPoint : Point of execution of the cibled method.
     * @param retryAnnotation : Check if an annotation should be returned
     * @return : ProceedingJoinPoint
     * @throws Throwable : Throwable object
     */
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

    /**
     *
     * @param excludes : List of throwable object excluded from the retry process
     * @param includes : List of throwable object included from the retry process
     * @param exception : The intercepted exception
     * @return : boolean
     */
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
