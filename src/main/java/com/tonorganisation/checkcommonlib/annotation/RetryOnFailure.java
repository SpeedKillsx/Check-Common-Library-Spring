package com.tonorganisation.checkcommonlib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryOnFailure {
    int maxAttempts() default 5;
    long delayMs() default 500;
    Class<? extends Throwable>[] includes() default {};
    Class<? extends Throwable>[] excludes() default {};
}
