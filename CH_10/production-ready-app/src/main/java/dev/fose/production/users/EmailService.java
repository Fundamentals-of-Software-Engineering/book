package dev.fose.production.users;

import dev.fose.production.config.AppConfiguration;
import dev.fose.production.config.ServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final AppConfiguration config;

    public EmailService(AppConfiguration config) {
        this.config = config;
    }

    public void sendWelcomeEmail(User user) {
        if (!config.getEmailService().isEnabled()) {
            logger.info("Email service is disabled, skipping welcome email");
            return;
        }

        try {
            logger.info("Sending welcome email to {} at {}", user.getUsername(), user.getEmail());
            logger.debug("Email sent successfully to user: {}", user.getUsername());
        } catch (Exception e) {
            logger.error("Failed to send welcome email to {}: {}", user.getEmail(), e.getMessage());
            throw new ServiceUnavailableException("Email Service", e);
        }
    }
}
