package dev.fose.production.config;

public class ServiceUnavailableException extends RuntimeException {

    public ServiceUnavailableException(String serviceName, Throwable cause) {
        super("Service unavailable: " + serviceName, cause);
    }

    public ServiceUnavailableException(String serviceName) {
        super("Service unavailable: " + serviceName);
    }
}
