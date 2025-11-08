package dev.fose.production.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Feature flags allow enabling/disabling features at runtime without code changes.
 * Useful for gradual rollouts, A/B testing, and emergency feature toggles.
 */
@Configuration
@ConfigurationProperties(prefix = "features")
public class FeatureFlags {

    private boolean advancedSearch = false;
    private boolean emailNotifications = false;
    private boolean debugMode = false;

    public boolean isAdvancedSearch() {
        return advancedSearch;
    }

    public void setAdvancedSearch(boolean advancedSearch) {
        this.advancedSearch = advancedSearch;
    }

    public boolean isEmailNotifications() {
        return emailNotifications;
    }

    public void setEmailNotifications(boolean emailNotifications) {
        this.emailNotifications = emailNotifications;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
