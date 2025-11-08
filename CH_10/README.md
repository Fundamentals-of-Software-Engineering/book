# Chapter 10: To Production

This directory contains code examples for Chapter 10 of "Fundamentals of Software Engineering," 
which covers the journey of taking code from development to production.

## Overview

This chapter demonstrates production-ready practices including environment-specific configurations, 
proper error handling and logging, security essentials, deployment automation, and CI/CD pipelines. 
The examples show how to build applications that are ready for the real world.

## Recommended Learning Path

If you're new to production deployments, follow this order:

1. `production-ready-app/` → See production-ready patterns in action
2. `ci-cd-pipeline/` → Understand automated deployment workflows

## Examples

### 1. `production-ready-app/`
**Concepts:** Environment configs, error handling, logging, security, monitoring
- Spring Boot application demonstrating production-ready patterns
- Environment-specific configuration (dev, prod)
- Proper error handling with graceful degradation
- Structured logging with appropriate levels
- Security best practices (no hardcoded secrets)
- Health check endpoints for monitoring
- **Run:** `./mvnw spring-boot:run` or `./mvnw spring-boot:run -Dspring-boot.run.profiles=prod`

### 2. `ci-cd-pipeline/`
**Concepts:** Continuous Integration, Continuous Deployment, GitHub Actions
- Simple Java application with automated CI/CD
- GitHub Actions workflow for build, test, and package
- Demonstrates the deployment pipeline from the book
- Automated testing on push and pull requests
- **Run:** `mvn test` (CI/CD runs automatically on push to GitHub)

## Prerequisites

- Java 21 or higher
- Maven 3.9+ (or use included Maven wrapper `./mvnw`)
- Understanding of Spring Boot basics (for example 1)
- Git and GitHub account (for CI/CD example)

## Key Learning Objectives

After working through these examples, you should understand:

1. **Production-Ready Code**
    - How to configure applications for different environments
    - Proper error handling and logging strategies
    - Security essentials (HTTPS, password encoding, secrets management)
    - Why production readiness starts in development

2. **Deployment Pipeline**
    - The role of different environments (dev, test, staging, production)
    - How to automate deployments and rollbacks
    - CI/CD workflows with GitHub Actions
    - Deployment strategies (gradual vs all-at-once)

3. **Monitoring and Maintenance**
    - Setting up application health checks
    - Implementing structured logging
    - Managing dependencies and security updates
    - Real-time monitoring vs historical logs

## Running All Examples

### Quick Start

Each example is a standalone project. To run any individual example:

```bash
cd <example-directory>
mvn clean test
```

### Run the Production-Ready App

```bash
cd production-ready-app

# Run in development mode (default)
./mvnw spring-boot:run

# Run in production mode
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod

# Test the application
curl http://localhost:8080/actuator/health
curl http://localhost:8080/api/users
```

### Test the CI/CD Pipeline

```bash
cd ci-cd-pipeline
mvn clean test

# To see the GitHub Actions workflow in action:
# 1. Fork or create a repository
# 2. Push the code to GitHub
# 3. Watch the Actions tab for automated builds
```

### Requirements

- **Java:** 21 or higher
- **Maven:** 3.9+ (or use included Maven wrapper `./mvnw`)
- **Optional:** Docker (for containerization examples)

## Key Takeaways

- **Think production from day one**: Build production readiness into your code from the start
- **Configuration is code**: Use environment-specific configs, never hardcode values
- **Security matters**: Protect secrets, use HTTPS, encode passwords properly
- **Automate everything**: Deployment scripts and CI/CD eliminate human error
- **Monitor actively**: Don't wait for users to report problems
- **Maintain regularly**: Keep dependencies updated and systems patched

## Additional Resources

The chapter references these excellent resources for deeper learning:
- Continuous Delivery by Jez Humble and David Farley
- The Phoenix Project by Gene Kim et al.
- Head First Git by Raju Gandhi
- Learning GitHub Actions by Brent Laster
- Feature Flags by Ben Nadel
