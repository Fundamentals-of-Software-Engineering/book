# AI Integration Demo

This Spring Boot application demonstrates practical AI integration patterns from Chapter 15, 
showing how to use Large Language Models (LLMs) in Java applications through REST endpoints.

## What's Inside

Two practical AI-powered REST APIs:

1. **Test Data Generator** (`/api/users/test-data`) - Generates realistic user profiles for testing
2. **Code Documentation Generator** (`/api/code/documentation`) - Auto-generates JavaDoc comments

Both examples use Spring AI to provide clean abstractions over OpenAI's API.

## Prerequisites

- Java 25 or higher
- Maven 3.8+
- OpenAI API key ([get one here](https://platform.openai.com/api-keys))

## Setup

1. **Get an API key** from https://platform.openai.com/api-keys

2. **Set the environment variable:**
   ```bash
   export OPENAI_API_KEY=your-actual-api-key-here
   ```

3. **Build the project:**
   ```bash
   mvn clean install
   ```

## Running the Demo

Start the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

Run tests (no API key required):
```bash
mvn test
```

## Usage Examples

### Generate Test Data

Generate 5 realistic user profiles:

```bash
curl "http://localhost:8080/api/users/test-data?count=5"
```

**Example Response:**
```json
{
  "users": [
    {
      "name": "Sarah Johnson",
      "email": "sarah.johnson@example.com",
      "age": 28,
      "interests": "Photography, hiking, cooking"
    },
    {
      "name": "Michael Chen",
      "email": "m.chen@example.com",
      "age": 35,
      "interests": "Gaming, technology, cycling"
    }
    // ... 3 more users
  ]
}
```

**How it works:**
- Sends a structured prompt to the AI with the desired count
- AI generates realistic user data
- Spring AI automatically converts JSON response to Java `User` records

### Generate Code Documentation

Generate JavaDoc for a method:

```bash
curl -G "http://localhost:8080/api/code/documentation" \
  --data-urlencode 'code=public int calculateTotal(List<Item> items) {
    return items.stream()
        .mapToInt(Item::getPrice)
        .sum();
}'
```

**Example Response:**
```java
/**
 * Calculates the total price of all items in the provided list.
 *
 * @param items the list of items to calculate the total for
 * @return the sum of all item prices
 */
```

**How it works:**
- Sends code snippet to the AI with instructions for JavaDoc format
- AI analyzes the method and generates appropriate documentation
- Returns formatted JavaDoc comment block

## Understanding the Code

### TestDataGenerator (`src/main/java/com/fose/aidemo/TestDataGenerator.java`)

Key features:
- `@RestController` with endpoint at `/api/users/test-data`
- Uses Spring AI's `ChatClient` for LLM interaction
- Structured prompts with parameters: `.user(u -> u.text(prompt).param("n", count))`
- Automatic JSON-to-Java conversion: `.entity(UserList.class)`
- Clean data models using Java records

### CodeDocumentationGenerator (`src/main/java/com/fose/aidemo/CodeDocumentationGenerator.java`)

Key features:
- `@RestController` with endpoint at `/api/code/documentation`
- Template-based prompting with code parameter
- Detailed prompt instructions for comprehensive JavaDoc
- Returns plain text documentation: `.content()`

## Key Takeaways

From Chapter 15:

1. **AI as a productivity tool** - Automate repetitive tasks like test data generation and documentation
2. **Clean integration** - Spring AI makes adding AI capabilities straightforward
3. **Structured prompts** - Clear, detailed prompts produce better results
4. **Always review AI output** - You're responsible for validating generated content
5. **API key security** - Never commit keys to version control; use environment variables

**Remember:** AI amplifies your skills. Use it for repetitive tasks so you can focus on complex problems requiring human judgment.

## Learning More

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [OpenAI API Documentation](https://platform.openai.com/docs/)
- Chapter 15: The AI-Powered Software Engineer
