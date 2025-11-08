# Prompt Engineering Guide

Prompt engineering is simply learning how to effectively communicate with AI. This guide provides
practical techniques and examples to help you get better results from AI tools, whether you're using
standalone chatbots, IDE assistants, or agentic environments.

## Table of Contents

- [Essential Techniques](#essential-techniques)
- [Advanced Techniques](#advanced-techniques)
- [AI Tools Comparison](#ai-tools-comparison)
- [Prompt Template Library](#prompt-template-library)

---

## Essential Techniques

### 1. Clear Communication is Key

The quality of your prompt directly correlates with the quality of the AI's response. Be specific
about what you need.

#### Bad Example
```
Write some Java code for sorting
```

**Why it's bad:** Too vague. Doesn't specify what to sort, how to sort it, or what data structure to use.

#### Good Example
```
Write a Java method that sorts an ArrayList of Employee objects by their salary
in descending order. Include error handling for null inputs.
```

**Why it's good:** Specifies data structure (ArrayList), what to sort (Employee objects), criteria (salary),
order (descending), and edge cases (null handling).

---

### 2. Structure Determines Success

Well-structured prompts help AI understand not just what you want, but how you want it delivered.

#### Bad Example
```
Explain database indexing
```

**Why it's bad:** No guidance about depth, perspective, or format. Could get anything from one sentence
to a graduate-level explanation.

#### Good Example
```
As an experienced Java developer, explain database indexing in 3 parts:

1. What indexes are and why they matter for performance
2. When to use clustered vs non-clustered indexes
3. One practical example of creating an index in PostgreSQL

Keep each section to 2-3 sentences and include one code example.
```

**Why it's good:** Clear structure (three parts), specifies audience (Java developer), sets length
expectations (2-3 sentences), and requests concrete example.

---

### 3. Think of It as Teaching, Not Commanding

Guide the AI through your thought process using chain-of-thought prompting. Break complex tasks
into logical steps.

#### Bad Example
```
Optimize this code.

[paste your code here]
```

**Why it's bad:** Doesn't specify what "optimize" means - could be performance, memory, readability,
or something else.

#### Good Example
```
I need to optimize this Java method that searches through a large dataset.
First, help me identify the current time complexity. Then suggest specific
improvements. Finally, show me the refactored code with comments explaining
the performance gains.

[paste your code here]
```

**Why it's good:** Breaks task into steps, specifies optimization goal (performance for large datasets),
and requests explanations alongside code.

---

## Advanced Techniques

### Structuring Techniques

#### Zero-Shot Prompting

Ask the AI to perform a task without providing examples. Simple and effective for straightforward requests.

**Example:**
```
Create a Java class that implements the Observer pattern for a
stock price monitoring system.
```

**When to use:** When the task is well-defined and doesn't require specific style preferences.

---

#### One-Shot Prompting

Provide a single example to establish the pattern you want the AI to follow.

**Example:**
```
// Here's an example of the coding style I prefer:

public Optional<User> findUserByEmail(String email) {
    if (email == null || email.trim().isEmpty()) {
        return Optional.empty();
    }
    return userRepository.findByEmail(email.toLowerCase());
}

// Now create a similar method called findUserById that takes a Long id parameter.
```

**When to use:** When you want AI to match a specific coding style or pattern from your codebase.

---

#### Few-Shot Prompting

Teach AI through multiple examples showing the desired input-output pattern.

**Example:**
```
Classify the sentiment of these customer reviews:

Example 1: "The software is intuitive and saves me hours of work" → Positive
Example 2: "Great documentation and excellent support team" → Positive
Example 3: "Buggy interface and crashes frequently" → Negative

Now classify: "The new features are helpful but the UI is confusing"
```

**When to use:** For classification tasks or when you need the AI to learn a pattern from examples.

---

### Organizational Techniques

#### XML Tags (Structured Formatting)

Use XML, Markdown, or JSON to clearly separate different types of information.

**Example:**
```xml
<task>
Create a RESTful API endpoint for user management
</task>

<requirements>
- POST /users for creating users
- Include validation for email and password
- Return appropriate HTTP status codes
- Use Spring Boot annotations
</requirements>

<constraints>
- Java 17
- Spring Boot 3.0
- No external dependencies beyond Spring starter
</constraints>
```

**Why it works:** Helps AI distinguish between what you want (task), what it must include (requirements),
and limitations (constraints). Reduces ambiguity and missed details.

---

#### Task Decomposition

Break complex problems into manageable pieces, just like you would for yourself or a junior developer.

**Example:**
```
I'm building a file processing system in Java. Help me break this down:

Phase 1: Design the file reading strategy (stream vs batch)
Phase 2: Create the data validation logic
Phase 3: Implement error handling and logging
Phase 4: Add unit tests

Start with Phase 1 - analyze the pros and cons of each approach
for processing 1GB+ files.
```

**Why it works:** Prevents AI from being overwhelmed by large requests. Allows for more thorough
analysis of each component and adjustment along the way.

---

## Practical Tips for Immediate Improvement

1. **Be specific about context**
   - Greenfield vs legacy project?
   - Personal vs mission-critical application?
   - Team size and experience level?

2. **Use examples**
   - Show AI what good output looks like
   - Provide code samples matching your preferred style

3. **Give environment details**
   - Java version, frameworks, constraints
   - Example: "Using Java 21, Spring Boot 3.2, PostgreSQL 15"

4. **Specify a role**
   - "Act as a senior Java architect reviewing this code"
   - "As a performance optimization expert, analyze this query"

5. **Iterate and refine**
   - Don't expect perfection on first try
   - Use AI's response to refine your prompt

6. **Use AI to improve prompts**
   - Ask: "Can you suggest how I should rephrase my request to get more specific debugging steps?"

7. **Save good prompts**
   - Build your personal prompt library
   - Reuse successful patterns for similar tasks

---

## AI Tools Comparison

Understanding which AI tool to use for different situations maximizes your productivity.

### Standalone Chatbot Assistants

**Examples:** ChatGPT, Google Gemini, Anthropic Claude

**Best for:**
- Understanding new concepts and frameworks
- Debugging complex problems
- Planning architecture decisions
- Learning through extended conversations
- Code generation with detailed explanations

**Use when:**
- You need thoughtful explanations, not just code
- Working through complex problem-solving
- Learning new technologies
- Discussing trade-offs and alternatives

**Best practices:**
- Provide context about what you're trying to accomplish
- Share relevant code snippets for specific problems
- Specify your role and AI's persona
- Ask for explanations, not just solutions

**Example prompt:**
```
I'm a Java developer building a REST API for an e-commerce platform.
I need to handle user authentication. Can you explain the trade-offs
between JWT tokens and session-based authentication, then recommend
an approach for my use case where we expect 10,000+ concurrent users?
```

---

### Inline IDE Assistants

**Examples:** GitHub Copilot, JetBrains AI Assistant, Amazon CodeWhisperer

**Best for:**
- Writing repetitive code (getters/setters, constructors)
- Implementing standard patterns (singleton, builder)
- Generating unit tests
- Creating comments and documentation
- Quick API and syntax suggestions

**Use when:**
- Writing boilerplate code
- Implementing well-known patterns
- Need real-time code completion
- Documenting existing code

**Best practices:**
- Review generated code before accepting
- Use suggestions as starting points
- Pay attention to patterns AI learns from your codebase
- Don't let AI override architectural decisions
- Start with simple tasks, gradually use for complex patterns

**Tip:** Always ask yourself, "Do I understand what this code does and why?" If not, research it.
You should be able to explain every line during code review.

---

### Agentic AI IDE Environments

**Examples:** Cursor, Junie, Cline

**Best for:**
- Large-scale refactoring across multiple files
- Implementing features requiring changes in several places
- Code migration or modernization tasks
- Understanding broader project context
- Complex multi-file operations

**Use when:**
- You need AI to understand entire codebase
- Implementing features spanning multiple files
- Refactoring that affects several components
- Migrating to new frameworks or patterns

**Best practices:**
- Start with clear, specific requirements
- Define nonfunctional requirements up front (style, security, testing)
- Review ALL changes carefully before committing
- Use version control to track AI changes
- Break large tasks into smaller pieces

**Example workflow:**
```
Task: Create a new StatusWidget for dashboard

AI Analysis Phase:
- Reviews existing widgets to understand patterns
- Identifies base Widget class and common methods
- Notices data service pattern and styling conventions

AI Planning Phase:
- Plans StatusWidget extending base class
- Plans StatusService for system metrics
- Plans dashboard registry updates

AI Implementation:
- Creates StatusWidget.java following patterns
- Creates StatusService.java matching architecture
- Updates dashboard configuration
- Applies consistent styling
```

---

## Prompt Template Library

Copy and customize these templates for common development tasks.

### Code Review
```
Review this [language] code for:
1. Potential bugs or edge cases
2. Performance concerns
3. Security vulnerabilities
4. Code readability and maintainability
5. Adherence to [specific] best practices

[paste code here]

Provide specific suggestions with examples.
```

### Debugging
```
I'm encountering [describe error/behavior] in this [language] code.

<context>
- Framework: [e.g., Spring Boot 3.2]
- Environment: [e.g., Java 21, PostgreSQL 15]
- What I've tried: [list debugging steps]
</context>

<code>
[paste relevant code]
</code>

<error>
[paste error message or unexpected behavior]
</error>

Please help me:
1. Identify the root cause
2. Suggest a fix with explanation
3. Recommend how to prevent this in the future
```

### Documentation Generation
```
Generate comprehensive documentation for this [language] code:

<code>
[paste code here]
</code>

Include:
- High-level purpose and usage
- Parameter descriptions
- Return value explanation
- Example usage
- Edge cases and error handling
- [JavaDoc/JSDoc/etc.] formatted comments
```

### Test Generation
```
Generate unit tests for this [language] code using [testing framework]:

<code>
[paste code here]
</code>

Include tests for:
- Happy path scenarios
- Edge cases (null, empty, boundary values)
- Error conditions
- [any specific scenarios]

Follow the style of our existing tests:
[paste example test if available]
```

### Refactoring
```
Refactor this [language] code to improve [specific aspect]:

<current_code>
[paste code here]
</current_code>

<goals>
- [e.g., Improve readability]
- [e.g., Reduce complexity]
- [e.g., Follow SOLID principles]
- [e.g., Optimize performance]
</goals>

<constraints>
- Must maintain backward compatibility
- [other constraints]
</constraints>

Show before/after comparison and explain the improvements.
```

### Code Conversion
```
Convert this [source language] code to [target language]:

<source_code>
[paste code here]
</source_code>

<requirements>
- Follow [target language] best practices and idioms
- Use [specific framework/libraries if applicable]
- Maintain the same functionality and edge case handling
- Add comments explaining any language-specific differences
</requirements>

After conversion, highlight any differences in behavior or performance.
```

### Learning New Technology
```
I'm learning [technology/framework] as a [your experience level] developer
with experience in [related technologies].

Create a learning plan that:
1. Explains core concepts building on what I already know
2. Provides 3-5 hands-on exercises progressing in difficulty
3. Highlights common pitfalls for developers with my background
4. Recommends specific resources for deeper learning

Focus on practical application rather than theory.
```

---

## Key Reminders

- **Always review AI-generated code** - Treat it as a first draft requiring testing and review
- **You're responsible for the code** - Whether human or AI wrote it, you must understand it
- **Save successful prompts** - Build your personal library over time
- **Iterate and improve** - Refine prompts based on results
- **Context is crucial** - The more relevant context you provide, the better the results
- **Quality > Speed** - Time saved on generation should be reinvested in validation

Remember: AI is your pair programming partner, not your replacement. Use these techniques to amplify
your skills and free up time for higher-level problem-solving.
