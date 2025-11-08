# Fundamentals of Software Engineering

This repository contains the source code for the book Fundamentals of Software Engineering.

Get the book
- **O'Reilly Learning:** Sign up for the [O'Reilly Learning Platform](https://learning.oreilly.com/library/view/fundamentals-of-software/9781098143220/) and get immediate access to this book plus thousands of other technical resources.
- **Amazon:** Pre-order your copy now on [Amazon](https://www.amazon.com/Fundamentals-Software-Engineering-Coder-Engineer/dp/109814323X/) and have it delivered as soon as it's available.

## About The Book

The gap between knowing how to code and being an effective engineer is real. This book fills that gap.

Boot camps and universities teach you how to write syntactically correct programs, but being a software engineer requires so much more. Whether you're an aspiring engineer beginning your career, an experienced developer seeking to formalize your knowledge, or a technical lead establishing team standards, this book is for you.

Written by Nate Schutta and Dan Vega, this comprehensive guide covers the fundamentals that will last a lifetime: reading and writing maintainable code, automated testing, working with existing codebases, software architecture, user interface design, data management, production deployment, and professional development. You'll also learn how to effectively integrate AI tools into your workflow.

These aren't trendy frameworks that will become obsolete next year. These are timeless fundamentals that will serve you throughout your entire career. Even as AI and agentic coding tools become more powerful, you need a solid grasp of these fundamentals to wield them effectively and advance to senior roles.

## Table of Contents

**Part 1: Core Skills**
- CH 01: Programmer to Engineer - Making the transition from coding to enginee ring thinking
- CH 02: Reading Code - Strategies for understanding existing codebases
- CH 03: Writing Code - Best practices for creating maintainable, quality code

**Part 2: Technical Practices**
- CH 04: Software Modeling - Using diagrams and models to design software systems
- [CH 05: Automated Testing](./CH_05/README.md) - Building confidence through different testing approaches
- [CH 06: Working with Existing Code](./CH_06/README.md) - Safely navigating and changing unknown code

**Part 3: Application Development and Design**
- CH 07: User Interface Design - Creating usable and accessible interfaces
- [CH 08: Working with Data](./CH_08/README.md) - Managing data types, storage, and performance
- CH 09: Software Architecture - Understanding trade-offs and architectural decisions
- [CH 10: To Production](./CH_10/README.md) - Deploying and maintaining code in real environments

**Part 4: Professional Development and Growth**
- CH 11: Powering Up Your Productivity - Optimizing your development workflow
- CH 12: Learning to Learn - Effective strategies for continuous skill development
- CH 13: Mastering Soft Skills in the Tech World - Communication and collaboration
- CH 14: Career Management - Planning and navigating your professional path
- CH 15: The AI-Powered Software Engineer - Working effectively with AI tools

## Running the Code Examples

This repository is organized by chapter, with each code example in its own directory. Since the book covers multiple 
technologies and languages, each example is self-contained with its own build configuration and dependencies.

### Prerequisites

Depending on which examples you want to run, you may need:

- **Java 17 or higher** - For Java and Spring Boot examples
- **Maven 3.8+** or **Gradle 7+** - For Java project builds
- **Node.js 18+** - For JavaScript/TypeScript examples
- **Python 3.9+** - For Python examples
- **Docker** - For containerized examples (optional)


### How to Run Examples

1. **Navigate to the chapter directory** you're interested in:
```bash
   cd CH_05
```

2. **Check the chapter's README** for an overview of available examples:
```bash
   cat README.md
```

3. **Enter the specific example directory**:
```bash
   cd example-name
```

4. **Follow the example-specific instructions**. Each example includes a README with:
    - Purpose and concepts demonstrated
    - Prerequisites for that specific example
    - Build and run commands
    - Expected output

### Troubleshooting

If you encounter issues:

1. Verify you have the required prerequisites installed
2. Check the example's README for specific version requirements
3. Ensure you're in the correct directory when running commands
4. For Java projects, try `./mvnw clean` or `./gradlew clean` before building
5. Check the [Issues](https://github.com/yourusername/yourrepo/issues) section or create a new issue

### Contributing

Found a bug or have an improvement? We welcome contributions! Please see [CONTRIBUTING.md](./CONTRIBUTING.md) for guidelines.