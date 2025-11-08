package dev.fose.production.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration loaded from environment-specific YAML files.
 * Demonstrates how to externalize configuration for different environments.
 */
@Configuration
@ConfigurationProperties(prefix = "services")
public class AppConfiguration {

    private PaymentApi paymentApi = new PaymentApi();
    private EmailService emailService = new EmailService();

    public PaymentApi getPaymentApi() {
        return paymentApi;
    }

    public void setPaymentApi(PaymentApi paymentApi) {
        this.paymentApi = paymentApi;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public static class PaymentApi {
        private String url;
        private String apiKey;
        private int timeout;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }

    public static class EmailService {
        private String url;
        private boolean enabled;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }
}
