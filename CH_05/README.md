# Chapter 5: Automated Testing

This directory contains code examples for Chapter 5 of "Fundamentals of Software Engineering," which covers 
the principles and practices of automated testing.

## Overview

This chapter demonstrates how automated testing serves as documentation, improves maintainability, and boosts developer
confidence. The examples progress from basic unit tests through integration testing to end-to-end testing, following
the testing pyramid model.

## Recommended Learning Path

If you're new to testing, follow this order:

1. `basic-assertions/` → Learn JUnit basics
2. `unit-testing-fundamentals/` → See why testable code matters ⚡
3. `mocking-with-mockito/` → Isolate dependencies
4. `integration-testing/` → Test components together
5. `spring-boot-testing/` → E2E testing in Spring
6. `payment-processor-example/` → Tests as documentation
7. `code-coverage-demo/` → Understand coverage metrics ⚡
8. `test-driven-development/` → Learn TDD workflow

⚡ = Includes "bad vs good" comparisons

## Examples

### 1. `basic-assertions/`
**Concepts:** JUnit 5 basics, assertion methods, test structure
- Simple mathematical operations class (`Operations.java`)
- Basic unit tests with various assertions
- Edge case testing examples
- **Run:** `mvn test`

### 2. `unit-testing-fundamentals/`
**Concepts:** Unit testing, test organization, naming conventions
- `BlogPostController` example (before and after refactoring)
- Demonstrates single responsibility principle through testing
- Test-first vs test-last approaches
- **Run:** `mvn test`

### 3. `mocking-with-mockito/`
**Concepts:** Mocking, test doubles, dependency isolation
- `UserService` with repository and email service dependencies
- Mock creation with `@Mock` annotation
- Behavior verification with Mockito
- Testing in isolation examples
- **Run:** `mvn test`

### 4. `integration-testing/`
**Concepts:** Testing component interactions, real dependencies
- `UserService` integration test with actual dependencies
- Database interaction testing
- Email service integration
- Test data management
- **Run:** `mvn test -Dtest=*IntTest`

### 5. `spring-boot-testing/`
**Concepts:** Spring Boot testing, REST API testing, E2E tests
- `@SpringBootTest` configuration
- `MockMvc` for API testing
- Database verification in tests
- Complete user registration flow example
- **Run:** `./mvnw test`

### 6. `payment-processor-example/`
**Concepts:** Tests as documentation, descriptive test names
- `PaymentProcessor` with multiple payment types
- Comprehensive test suite demonstrating features
- Tests that reveal business rules (e.g., unsupported payment methods)
- **Run:** `mvn test`

### 7. `code-coverage-demo/`
**Concepts:** Code coverage tools, meaningful vs vanity metrics
- Example showing high coverage with poor tests
- Example showing meaningful coverage with good tests
- JaCoCo integration for coverage reports
- **Run:** `mvn test jacoco:report` (view report in `target/site/jacoco/index.html`)

### 8. `test-driven-development/`
**Concepts:** TDD cycle, red-green-refactor
- Simple calculator built using TDD
- Step-by-step commits showing the TDD process
- Refactoring with confidence
- **Run:** `mvn test`

## Prerequisites

- Java 17 or higher
- Maven 3.8+ or Gradle 7+
- IDE with JUnit support (IntelliJ IDEA, Eclipse, or VS Code)

## Common Dependencies

All Maven projects in this chapter use these core testing dependencies:
- JUnit 5 (Jupiter) - Core testing framework
- Mockito - Mocking framework
- AssertJ - Fluent assertion library (optional)
- Spring Boot Test Starter (for Spring examples)

## Key Learning Objectives

After working through these examples, you should understand:

1. **The Testing Pyramid**
    - Why unit tests form the foundation
    - When to use integration vs E2E tests
    - The trade-offs between test types

2. **Writing Effective Tests**
    - How to write clear, maintainable tests
    - Using descriptive test names as documentation
    - The importance of test isolation

3. **Mocking and Test Doubles**
    - When and how to use mocks
    - Isolating systems under test
    - Verifying behavior vs state

4. **Code Coverage**
    - Using coverage as a guide, not a goal
    - Identifying untested critical paths
    - Avoiding coverage gaming

## Running All Examples

### Quick Start

Each example is a standalone Maven project. To run any individual example:

```bash
cd <example-directory>
mvn clean test
```

### Run All Tests

To run tests for all examples in sequence:

```bash
# From the CH_05 directory
for dir in basic-assertions unit-testing-fundamentals mocking-with-mockito integration-testing spring-boot-testing payment-processor-example code-coverage-demo test-driven-development gh-actions; do
  echo "Testing $dir..."
  (cd $dir && mvn clean test)
done
```

### Generate Coverage Reports

For the `code-coverage-demo` example:

```bash
cd code-coverage-demo
mvn clean test jacoco:report
open target/site/jacoco/index.html  # macOS
# Or navigate to target/site/jacoco/index.html in your browser
```

### Requirements

- **Java:** 21 or higher
- **Maven:** 3.9+ (or use included Maven wrapper `./mvnw`)
- **Optional:** IDE with JUnit support for running tests individually

### Troubleshooting

If tests fail to run:
1. Verify Java version: `java -version` (should be 21+)
2. Verify Maven version: `mvn -version` (should be 3.9+)
3. Clean and rebuild: `mvn clean compile`
4. Check for port conflicts (Spring Boot examples use random ports)

### Tips

- **Start simple**: Begin with `basic-assertions/` before moving to mocking
- **Compare contrasts**: Study `unit-testing-fundamentals/` and `code-coverage-demo/` to see bad vs good patterns
- **Use your IDE**: Most IDEs can run individual tests - right-click and "Run Test"
- **Check coverage**: Run `code-coverage-demo` to see how JaCoCo visualizes coverage
- **Follow TDD**: Try `test-driven-development/` and write your own feature using red-green-refactor


### All Tests 


Test Results Summary:

| Example                       | Status | Notes                                          |
|-------------------------------|--------|------------------------------------------------|
| 1. basic-assertions/          | ✅ PASS | 12 tests - JUnit lifecycle methods working     |
| 2. unit-testing-fundamentals/ | ✅ PASS | 11 tests - Both bad and good examples          |
| 3. mocking-with-mockito/      | ✅ PASS | 5 tests - Mockito mocks working                |
| 4. integration-testing/       | ✅ PASS | 5 tests - Real implementations tested          |
| 5. spring-boot-testing/       | ✅ PASS | 2 tests - Spring Boot 3.5.7 E2E tests          |
| 6. payment-processor-example/ | ✅ PASS | 11 tests - All payment scenarios covered       |
| 7. code-coverage-demo/        | ✅ PASS | 14 tests - Both bad and good coverage examples |
| 8. test-driven-development/   | ✅ PASS | 14 tests - Complete TDD cycle demonstrated     |
| 9. gh-actions/                | ✅ PASS | 4 tests - CI/CD example working                |