package com.fose.tracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class - Entry point for the Spring Boot application.
 *
 * This is one of the primary entry points into the application. When exploring
 * a Spring Boot codebase, look for @SpringBootApplication annotation to find
 * where the application starts.
 */
@SpringBootApplication
public class TracingRequestsApplication {

    /**
     * The main method is the entry point when the application starts.
     * You can set a breakpoint here and step through to see how Spring Boot
     * initializes the application context, scans for components, and starts
     * the embedded web server.
     */
    public static void main(String[] args) {
        SpringApplication.run(TracingRequestsApplication.class, args);
    }
}
