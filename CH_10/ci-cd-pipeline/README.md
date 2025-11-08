# CI/CD Pipeline Example

This example demonstrates a complete CI/CD pipeline using GitHub Actions, as described in Chapter 10.

## What This Example Demonstrates

### Continuous Integration (CI)
Every time code is pushed to the repository or a pull request is created, GitHub Actions automatically:
1. **Checks out the code** from the repository
2. **Sets up the build environment** (Java 21)
3. **Compiles the application** to catch syntax errors
4. **Runs all tests** to catch bugs
5. **Packages the application** into a deployable JAR file
6. **Stores the artifact** for deployment

### The CI/CD Workflow

The workflow is defined in `.github/workflows/ci-cd.yml` and follows these steps:

```yaml
Code Push → Checkout → Setup Java → Build → Test → Package → Store Artifact
```

This automation ensures:
- ✅ **Consistency**: Same build process every time
- ✅ **Early detection**: Bugs are caught before merging
- ✅ **Confidence**: All tests pass before deployment
- ✅ **Speed**: Automated builds are faster than manual

## Running Locally

### Prerequisites
- Java 21 or higher
- Maven 3.9+

### Build the Project
```bash
mvn clean compile
```

### Run Tests
```bash
mvn test
```

### Package the Application
```bash
mvn package
```

### Run the Application
```bash
java -jar target/ci-cd-pipeline-1.0.0.jar
```

## Setting Up CI/CD with GitHub

### 1. Create a GitHub Repository
```bash
# Initialize git (if not already done)
git init

# Add all files
git add .

# Commit your code
git commit -m "Initial commit with CI/CD pipeline"

# Add remote repository
git remote add origin https://github.com/YOUR_USERNAME/YOUR_REPO.git

# Push to GitHub
git push -u origin main
```

### 2. Watch the Magic Happen
Once you push your code to GitHub:

1. Navigate to your repository on GitHub
2. Click the **Actions** tab
3. You'll see the workflow running automatically
4. Click on the workflow run to see detailed logs
5. Watch each step execute in real-time

### 3. View Build Artifacts
After a successful build:
1. Go to the workflow run details
2. Scroll down to the **Artifacts** section
3. Download `calculator-jar` to get the built JAR file

## Understanding the Workflow

### When Does It Run?
The workflow runs automatically on:
- **Push to main**: When you push commits to the main branch
- **Pull requests**: When you create or update a pull request

### What Happens When Tests Fail?
If any test fails:
- ❌ The workflow stops and marks the build as failed
- 📧 You'll get a notification (if enabled)
- 🚫 The artifact won't be created
- 💡 You can see which test failed in the logs

This prevents broken code from being deployed!

### Key Components

```yaml
# Trigger: When should this run?
on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

# Jobs: What should run?
jobs:
  build-and-test:
    runs-on: ubuntu-latest  # Virtual machine

    steps:
      - uses: actions/checkout@v4    # Get the code
      - uses: actions/setup-java@v4  # Setup Java
      - run: mvn test                # Run tests
      # ... more steps
```

## Benefits of This Approach

### Before CI/CD
- ❌ Manual build process (easy to forget steps)
- ❌ "Works on my machine" syndrome
- ❌ Tests might not run before deployment
- ❌ Inconsistent build environments

### After CI/CD
- ✅ Automated, consistent builds
- ✅ Tests always run before deployment
- ✅ Same environment every time
- ✅ Fast feedback on issues
- ✅ Easy to see what changed and why

## Next Steps

To extend this pipeline, you could add:

1. **Code coverage reports**: Add JaCoCo plugin and upload coverage to Codecov
2. **Security scanning**: Add dependency vulnerability scanning
3. **Deployment**: Add a deploy step to push to staging/production
4. **Notifications**: Send Slack/Discord messages on build status
5. **Multiple environments**: Test on different Java versions

## Learn More

From the chapter, you learned about:
- Deployment environments (dev, staging, production)
- Version control strategies (Git Flow)
- Deployment automation and rollback procedures
- Canary releases and blue-green deployments

This example demonstrates the **build and test** portion of the pipeline. In a real project, you'd add deployment steps to push the artifact to your production environment.
