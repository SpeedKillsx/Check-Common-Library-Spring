package com.tonorganisation.checkcommonlib.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that retries for a number of given attempts an annotated method.
 *
 *  maxAttempts : Maximum number of attempts.
 *  delayMs : Delay in milliseconds between attempts.
 *  includes : List of exceptions that should trigger a retry.
 *  excludes : List of exceptions that should not trigger a retry.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RetryOnFailure {
    /**
     * Specifies the maximum number of retry attempts for the annotated method.
     *
     * @return the maximum number of retry attempts. Defaults to 5 if not provided.
     */
    int maxAttempts() default 5;

    /**
     * Specifies the delay in milliseconds between retry attempts for the annotated method.
     *
     * @return the delay in milliseconds between retry attempts. Defaults to 500 milliseconds.
     */
    long delayMs() default 500;

    /**
     * Specifies a list of exception classes that should trigger retries for the
     * @return an array of exception classes that are included from retry handling.
     *        Defaults to an empty array if not specified.
     * */
    Class<? extends Throwable>[] includes() default {};

    /**
     * Specifies a list of exception classes that should not trigger retries for the annotated method.
     * If an exception belonging to any of the classes listed in this array is thrown, the retry
     * mechanism will not be executed.
     *
     * @return an array of exception classes that are excluded from retry handling.
     *         Defaults to an empty array if not specified.
     */
    Class<? extends Throwable>[] excludes() default {};
}
