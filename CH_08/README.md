# Chapter 8: Working with Data

This directory contains code examples for Chapter 8 of "Fundamentals of Software Engineering," which covers
the principles and practices of working with data effectively in software applications.

## Overview

This chapter demonstrates how to work with different data types, select appropriate storage solutions, design
efficient data models, optimize queries, and manage data evolution over time. The examples progress from basic
data type handling through database operations to caching strategies and data migrations.

## Recommended Learning Path

If you're new to data management, follow this order:

1. `data-types-demo/` → Learn about structured/unstructured data and formats
2. `relational-database-basics/` → Understand JDBC and direct database access
3. `repository-pattern/` → See abstraction layers in action ⚡
4. `caching-strategies/` → Implement cache-aside, write-through, and write-behind
5. `query-optimization/` → Learn to write efficient queries ⚡
6. `data-migration-flyway/` → Manage schema changes with version control

⚡ = Includes "bad vs good" comparisons

## Examples

### 1. `data-types-demo/`
**Concepts:** Structured vs unstructured data, JSON, XML, CSV, YAML formats
- Customer data in multiple formats
- Binary data handling (image reading)
- Date/time data manipulation
- Data format conversion utilities
- **Run:** `mvn test`

### 2. `relational-database-basics/`
**Concepts:** Direct database access, JDBC, prepared statements, transactions
- Simple users and posts schema
- CRUD operations with JDBC
- Prepared statement examples
- Transaction management for data integrity
- **Run:** `mvn test` (uses H2 in-memory database)

### 3. `repository-pattern/`
**Concepts:** Data access abstraction, repository pattern, separation of concerns
- Direct JDBC access (before)
- Repository pattern implementation (after)
- Cleaner separation between business logic and data access
- Easier testing with mock repositories
- **Run:** `mvn test`

### 4. `caching-strategies/`
**Concepts:** Cache-aside, write-through, write-behind strategies
- Blog post caching example
- Three different caching implementations
- Performance comparison
- Trade-offs demonstration
- **Run:** `mvn test`

### 5. `query-optimization/`
**Concepts:** Query efficiency, SELECT optimization, pagination, indexing
- Inefficient query examples (SELECT *, no pagination)
- Optimized query examples (specific columns, LIMIT)
- Index creation and usage
- Query performance comparison
- **Run:** `mvn test`

### 6. `data-migration-flyway/`
**Concepts:** Schema versioning, Flyway migrations, data transformations
- Version-controlled schema changes
- Simple migrations (CREATE, ALTER)
- Java-based migration for complex transformations
- Address parsing example from chapter
- **Run:** `mvn clean test`

## Prerequisites

- Java 21 or higher
- Maven 3.8+ or Gradle 7+
- IDE with JUnit support (IntelliJ IDEA, Eclipse, or VS Code)
- Docker (optional, for PostgreSQL examples)

## Common Dependencies

All Maven projects in this chapter use these core dependencies:
- H2 Database - In-memory database for testing
- HikariCP - Connection pooling
- JUnit 5 (Jupiter) - Core testing framework
- Flyway - Database migration tool (where applicable)

## Key Learning Objectives

After working through these examples, you should understand:

1. **Data Types and Formats**
    - The difference between structured and unstructured data
    - When to use JSON, XML, CSV, or YAML
    - How to handle binary and date/time data

2. **Database Selection**
    - Different database types and their use cases
    - Trade-offs between relational and NoSQL databases
    - When to use document, key-value, or graph databases

3. **Data Persistence Patterns**
    - Direct database access with JDBC
    - Repository pattern for abstraction
    - When to use ORMs vs raw SQL

4. **Performance Optimization**
    - Writing efficient queries
    - Proper use of indexes
    - Caching strategies and trade-offs
    - Query planning and EXPLAIN

5. **Data Evolution**
    - Version-controlled schema changes
    - Safe data migrations
    - Handling schema transformations

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
# From the CH_08 directory
for dir in data-types-demo relational-database-basics repository-pattern caching-strategies query-optimization data-migration-flyway; do
  echo "Testing $dir..."
  (cd $dir && mvn clean test)
done
```

### Requirements

- **Java:** 21 or higher
- **Maven:** 3.8+ (or use included Maven wrapper `./mvnw`)
- **Optional:** Docker for running PostgreSQL locally

### Troubleshooting

If tests fail to run:
1. Verify Java version: `java -version` (should be 21+)
2. Verify Maven version: `mvn -version` (should be 3.8+)
3. Clean and rebuild: `mvn clean compile`
4. Check H2 database initialization in test logs

### Tips

- **Start simple**: Begin with `data-types-demo/` before diving into databases
- **Compare contrasts**: Study `repository-pattern/` and `query-optimization/` to see bad vs good patterns
- **Use H2 Console**: Many examples enable H2 console at `http://localhost:8080/h2-console`
- **Experiment with caching**: Modify TTL values in `caching-strategies/` to see different behaviors
- **Read EXPLAIN output**: The `query-optimization/` example shows how to interpret query plans


## Tests Passing

All examples successfully compile and pass all tests with Java 21:

| Example                    | Tests   | Status |
  |----------------------------|---------|--------|
| data-types-demo            | 6 tests | ✅ PASS |
| relational-database-basics | 7 tests | ✅ PASS |
| repository-pattern         | 4 tests | ✅ PASS |
| caching-strategies         | 7 tests | ✅ PASS |

## Additional Resources

From the chapter:
- Designing Data-Intensive Applications by Martin Kleppmann
- Seven Databases in Seven Weeks by Luc Perkins et al.
- Refactoring Databases by Scott W. Ambler and Pramod J. Sadalage
- Fundamentals of Data Engineering by Joe Reis and Matt Housley
