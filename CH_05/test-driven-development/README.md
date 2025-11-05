# Test-Driven Development (TDD) Example

This example demonstrates Test-Driven Development (TDD) methodology through building a simple Calculator class.

## What is TDD?

Test-Driven Development is a software development approach where you write tests **before** writing the actual code. It follows a three-phase cycle called **"Red-Green-Refactor"**:

### The Red-Green-Refactor Cycle

1. **🔴 RED**: Write a test that fails
   - Think about what you want to build
   - Write a test for that behavior
   - Run the test - it should fail (red bar) because the code doesn't exist yet

2. **🟢 GREEN**: Make the test pass
   - Write the **minimum** code needed to make the test pass
   - Don't over-engineer or add extra features
   - Run the test - it should pass (green bar)

3. **🔄 REFACTOR**: Improve the code
   - Clean up the code while keeping tests green
   - Remove duplication, improve names, extract methods
   - Run tests after each change to ensure nothing broke

## How This Calculator Was Built

This Calculator class was built feature-by-feature using TDD. Here's how the process worked:

### Cycle 1: Addition

```
1. RED   → Wrote test: assertEquals(5, calculator.add(2, 3));
           Test FAILED: Method add() doesn't exist

2. GREEN → Implemented: public int add(int a, int b) { return a + b; }
           Test PASSED: Green bar!

3. REFACTOR → No refactoring needed for simple addition
```

### Cycle 2: Subtraction

```
1. RED   → Wrote test: assertEquals(2, calculator.subtract(5, 3));
           Test FAILED: Method subtract() doesn't exist

2. GREEN → Implemented: public int subtract(int a, int b) { return a - b; }
           Test PASSED: Green bar!

3. REFACTOR → No refactoring needed
```

### Cycle 3: Multiplication

```
1. RED   → Wrote test: assertEquals(12, calculator.multiply(4, 3));
           Test FAILED: Method doesn't exist

2. GREEN → Implemented: public int multiply(int a, int b) { return a * b; }
           Test PASSED!

3. REFACTOR → Added tests for edge cases (multiply by zero, negative numbers)
```

### Cycle 4: Division

```
1. RED   → Wrote test: assertEquals(4, calculator.divide(8, 2));
           Test FAILED: Method doesn't exist

2. GREEN → Implemented: public int divide(int a, int b) { return a / b; }
           Test PASSED!

3. RED   → Realized we need to handle division by zero
           Wrote test: assertThrows(ArithmeticException.class, ...)
           Test FAILED: No validation yet

4. GREEN → Added: if (b == 0) throw new ArithmeticException(...)
           Test PASSED!

5. REFACTOR → Extracted validateDivisor() method for clarity
              All tests still pass!
```

### Cycle 5: Power

```
1. RED   → Wrote test: assertEquals(8, calculator.power(2, 3));
           Test FAILED: Method doesn't exist

2. GREEN → Implemented with loop:
           int result = 1;
           for (int i = 0; i < exponent; i++) { result *= base; }
           Test PASSED!

3. RED   → Added edge case tests (power of 0, power of 1, negative exponents)
           Some FAILED: Missing edge case handling

4. GREEN → Added edge case logic:
           if (exponent == 0) return 1;
           if (exponent < 0) throw exception;
           All tests PASSED!

5. REFACTOR → Code is clean, no refactoring needed
```

## Running the Tests

```bash
mvn test
```

All tests should pass, demonstrating that our Calculator works correctly.

## TDD in Real Projects

In a real project, you would commit after each phase:

```bash
# After RED phase
git add CalculatorTest.java
git commit -m "RED: Add test for calculator addition"

# After GREEN phase
git add Calculator.java
git commit -m "GREEN: Implement add() method"

# After REFACTOR phase
git add Calculator.java
git commit -m "REFACTOR: Extract validation logic"
```

This creates a clear history showing how features were developed test-first.

## Benefits of TDD

1. **Better Design**: Writing tests first forces you to think about the API before implementation
2. **Confidence**: Every feature has tests before you start coding
3. **Safety Net**: Can refactor confidently knowing tests will catch breakage
4. **Documentation**: Tests show how to use the code
5. **Less Debugging**: Catch bugs immediately when writing code, not weeks later

## Why Write Tests First?

- **Forces clarity**: Must understand requirements before coding
- **Simpler code**: Only write what's needed to pass tests
- **Better coverage**: Tests are never an afterthought
- **Faster development**: Catch issues immediately, not in QA

## Learn More

This example demonstrates the TDD cycle described in **Chapter 5** of the book:

> "Test-first methodologies, such as test-driven development (TDD), follow a 'red-green-refactor' cycle. First, you write a failing test ('red'), then implement the minimum code to pass the test ('green'), and finally improve the code without changing its behavior ('refactor')."

## Next Steps

Try extending this Calculator with TDD:

1. **RED**: Write a test for `sqrt(double n)`
2. **GREEN**: Implement just enough to pass
3. **REFACTOR**: Clean up the code
4. Repeat for other operations (factorial, modulo, etc.)

Remember: **Red → Green → Refactor!**
