package com.tonorganisation.checkcommonlib.resources;

import com.tonorganisation.checkcommonlib.annotation.AuditAnnotation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestLibCheck {
    @AuditAnnotation
    @GetMapping("/ok")
    public String check() {
        return "OK";
    }
}
