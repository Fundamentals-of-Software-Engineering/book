# Production-Ready Application Example

This Spring Boot application demonstrates production-ready patterns from Chapter 10.

## What This Example Demonstrates

### 1. Environment-Specific Configuration
- **Development mode** (`application-dev.yml`): Verbose logging, local services, all features enabled
- **Production mode** (`application-prod.yml`): Minimal logging, environment variables for secrets, selective features

Run in dev mode (default):
```bash
./mvnw spring-boot:run
```

Run in production mode:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### 2. Proper Error Handling
- **Meaningful error messages** for users (see `GlobalExceptionHandler.java`)
- **Technical details** logged for developers
- **Graceful degradation** when external services fail
- **Never exposes** sensitive information in errors

### 3. Structured Logging
- Different log levels for different environments (DEBUG in dev, WARN/ERROR in prod)
- Contextual information without PII
- See `UserService.java` for logging examples

### 4. Security Best Practices
- **Password encoding** with BCrypt (never store plain text)
- **No hardcoded secrets** - uses environment variables in production
- **Input validation** on all user inputs
- See `SecurityConfig.java` and `User.java`

### 5. Feature Flags
- Enable/disable features without code changes
- Different settings per environment
- See `FeatureFlags.java` and `application-*.yml`

### 6. Health Checks for Monitoring
Spring Boot Actuator provides monitoring endpoints:
```bash
# Check application health
curl http://localhost:8080/actuator/health

# View metrics (in dev mode)
curl http://localhost:8080/actuator/metrics
```

## Running the Application

### Prerequisites
- Java 21 or higher
- Maven 3.9+ (or use included `mvnw`)

### Start the Application
```bash
./mvnw spring-boot:run
```

### Test the API
```bash
# Create a user
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"johndoe","email":"john@example.com","password":"securePass123!"}'

# Get all users
curl http://localhost:8080/api/users

# Get user by ID
curl http://localhost:8080/api/users/1

# Test error handling (invalid user)
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"ab","email":"invalid","password":"short"}'
```

### Run Tests
```bash
./mvnw test
```

## Key Files to Review

1. **`application-dev.yml` vs `application-prod.yml`** - Compare environment configs
2. **`GlobalExceptionHandler.java`** - Error handling patterns
3. **`UserService.java`** - Logging and business logic
4. **`SecurityConfig.java`** - Password encoding setup
5. **`FeatureFlags.java`** - Feature flag implementation

## Production Considerations

This example demonstrates patterns, but in production you'd also need:

- **Database**: Replace in-memory storage with actual database (PostgreSQL, MySQL, etc.)
- **Secrets management**: Use AWS Secrets Manager, HashiCorp Vault, or similar
- **HTTPS**: Configure TLS certificates
- **Rate limiting**: Prevent abuse
- **Monitoring**: Integrate with Prometheus, Grafana, or similar
- **Distributed tracing**: Add Spring Cloud Sleuth for request tracking

## What You'll Learn

After exploring this example, you'll understand:
- How to configure apps for different environments
- Why you should never hardcode credentials
- How to handle errors gracefully
- When and what to log (and what NOT to log)
- How to use feature flags effectively
- Why health checks are essential for monitoring
