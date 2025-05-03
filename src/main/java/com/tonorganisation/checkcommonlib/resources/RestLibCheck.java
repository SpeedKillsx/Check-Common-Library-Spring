package com.tonorganisation.checkcommonlib.resources;

import com.tonorganisation.checkcommonlib.annotation.RetryOnFailure;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOError;
import java.io.IOException;

@RestController
public class RestLibCheck {

    @GetMapping("/ok")
    public String check() {
        return "OK";
    }

    @GetMapping("/green")
    public String greenCheck() {
        return "Green";
    }


    @GetMapping("/red")
    public String redCheck() {
        return "RED";
    }

    @GetMapping("/retry")
    @RetryOnFailure(
            maxAttempts = 3,
            delayMs = 200,
            includes = {IOError.class},
            excludes = {IOException.class}
    )
    public String MathError() {
        if (Math.random() < 0.8) {
            throw new IOError(new Throwable("Simulated IOError"));

        }
        return "OK after retries";
    }
}
