package com.tonorganisation.checkcommonlib.resources;

import com.tonorganisation.checkcommonlib.annotation.AuditAnnotation;
import com.tonorganisation.checkcommonlib.annotation.LogExecutionTimeAnnotation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestLibCheck {
    @AuditAnnotation
    @LogExecutionTimeAnnotation
    @GetMapping("/ok")
    public String check() {
        return "OK";
    }

    @AuditAnnotation
    @GetMapping("/green")
    public String greenCheck() {
        return "Green";
    }


    @LogExecutionTimeAnnotation
    @GetMapping("/red")
    public String redCheck() {
        return "RED";
    }
}
