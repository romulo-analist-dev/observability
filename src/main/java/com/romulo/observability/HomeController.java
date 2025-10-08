package com.romulo.observability;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/")
    public String HomePage() {
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("Welcome home page {}", localDateTime);

        return "Welcome to Home Page";
    }

    @GetMapping("/logs")
    public String LogsPage() {
        LocalDateTime localDateTime = LocalDateTime.now();
        log.info("This Logs page {}", localDateTime);

        return "Welcome to logs page";
    }

    @GetMapping("/warn")
    public String warnPage() {
        LocalDateTime localDateTime = LocalDateTime.now();

        log.warn("This warn page {}", localDateTime);
        return "Welcome to warn page";
    }

    @GetMapping("/er")
    public String errorPage() {
        LocalDateTime localDateTime = LocalDateTime.now();

        log.error("This error page {}", localDateTime);
        return "Welcome to error page";
    }
}
