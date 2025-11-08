# AI Coding Scenarios: Practical Exercises

These hands-on scenarios help you practice AI-assisted development using any AI tool you prefer
(ChatGPT, Claude, GitHub Copilot, etc.). Each scenario is based on the "Putting It into Practice"
section from Chapter 15 and includes prompts to try, what to look for, and space to document your
learnings.

**Before you start:** Choose one AI tool to use consistently across all scenarios. This helps you
understand that tool's strengths and limitations.

**My chosen AI tool:** ___________________________

---

## Scenario 1: Code Analysis and Understanding

**Goal:** Use AI to analyze existing code and compare its insights with your own understanding.

**Instructions:**
1. Select an existing method from your codebase (preferably something moderately complex)
2. Use the prompt below to ask AI for analysis
3. Compare AI's analysis with your own understanding
4. Look for insights you might have missed

**Prompt to use:**
```
Analyze this [language] method and provide:

1. A clear explanation of what it does
2. Potential improvements (performance, readability, maintainability)
3. Edge cases that should be tested
4. Any potential bugs or issues

<code>
[paste your code here]
</code>
```

**What to look for:**
- Does AI correctly understand the code's purpose?
- Are the suggested improvements valid and practical?
- Did AI identify edge cases you hadn't considered?
- Any hallucinations or incorrect assumptions?

**Your results:**
```
Method analyzed:
_____________________________________________________________________________

AI tool used:
_____________________________________________________________________________

Key insights from AI:
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________

Did you agree with AI's analysis? Why or why not?
_____________________________________________________________________________
_____________________________________________________________________________

Issues you found in AI's response:
_____________________________________________________________________________
_____________________________________________________________________________
```

---

## Scenario 2: Prompt Engineering Practice

**Goal:** Craft three well-structured prompts for common coding tasks and document what works.

**Instructions:**
1. Choose three common tasks from your development workflow
2. Write prompts using techniques from the Prompt Engineering Guide
3. Test each prompt with your AI tool
4. Refine based on results

**Suggested tasks:**
- Debugging a specific error
- Code review for a pull request
- Documentation generation
- Test case creation
- Refactoring for readability

**Task 1:**
```
Task type: _________________________________________________________________

Initial prompt:
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________

Result quality (1-5): ___/5

Refined prompt (if needed):
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________

Refined result quality (1-5): ___/5

What made the difference?
_____________________________________________________________________________
_____________________________________________________________________________
```

**Task 2:**
```
Task type: _________________________________________________________________

Initial prompt:
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________

Result quality (1-5): ___/5

Refined prompt (if needed):
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________

Refined result quality (1-5): ___/5

What made the difference?
_____________________________________________________________________________
_____________________________________________________________________________
```

**Task 3:**
```
Task type: _________________________________________________________________

Initial prompt:
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________

Result quality (1-5): ___/5

Refined prompt (if needed):
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________

Refined result quality (1-5): ___/5

What made the difference?
_____________________________________________________________________________
_____________________________________________________________________________
```

---

## Scenario 3: Building Your Prompt Library

**Goal:** Create a personal prompt library with 5-10 effective prompts for common development tasks.

**Instructions:**
1. Identify your most common development tasks
2. Craft effective prompts for each using techniques from the guide
3. Test and refine them
4. Save them for future use

**Example format:**

### Prompt 1: [Task Name]
```
[Your prompt template here]
```
**When to use:** [Description]
**Expected result:** [What you should get]

---

**Your Prompt Library:**

### Prompt 1: _______________________________
```
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________
```
**When to use:** ____________________________________________________________
**Expected result:** ________________________________________________________

### Prompt 2: _______________________________
```
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________
```
**When to use:** ____________________________________________________________
**Expected result:** ________________________________________________________

### Prompt 3: _______________________________
```
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________
```
**When to use:** ____________________________________________________________
**Expected result:** ________________________________________________________

### Prompt 4: _______________________________
```
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________
```
**When to use:** ____________________________________________________________
**Expected result:** ________________________________________________________

### Prompt 5: _______________________________
```
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________
```
**When to use:** ____________________________________________________________
**Expected result:** ________________________________________________________

*(Add more as you discover effective prompts)*

---

## Scenario 4: AI Tool Evaluation

**Goal:** Use one AI coding assistant for one week on a personal project and document its strengths
and weaknesses.

**Instructions:**
1. Choose a personal project (avoid production code for this exercise)
2. Use your chosen AI tool consistently for one week
3. Track what tasks it excels at and where it struggles
4. Document patterns you notice

**Project:** ________________________________________________________________

**Week of:** ________________________________________________________________

**Daily Log:**

**Day 1:**
```
Tasks attempted:
_____________________________________________________________________________

Successes:
_____________________________________________________________________________
_____________________________________________________________________________

Struggles:
_____________________________________________________________________________
_____________________________________________________________________________
```

**Day 2:**
```
Tasks attempted:
_____________________________________________________________________________

Successes:
_____________________________________________________________________________
_____________________________________________________________________________

Struggles:
_____________________________________________________________________________
_____________________________________________________________________________
```

**Day 3:**
```
Tasks attempted:
_____________________________________________________________________________

Successes:
_____________________________________________________________________________
_____________________________________________________________________________

Struggles:
_____________________________________________________________________________
_____________________________________________________________________________
```

**Day 4:**
```
Tasks attempted:
_____________________________________________________________________________

Successes:
_____________________________________________________________________________
_____________________________________________________________________________

Struggles:
_____________________________________________________________________________
_____________________________________________________________________________
```

**Day 5:**
```
Tasks attempted:
_____________________________________________________________________________

Successes:
_____________________________________________________________________________
_____________________________________________________________________________

Struggles:
_____________________________________________________________________________
_____________________________________________________________________________
```

**Day 6:**
```
Tasks attempted:
_____________________________________________________________________________

Successes:
_____________________________________________________________________________
_____________________________________________________________________________

Struggles:
_____________________________________________________________________________
_____________________________________________________________________________
```

**Day 7:**
```
Tasks attempted:
_____________________________________________________________________________

Successes:
_____________________________________________________________________________
_____________________________________________________________________________

Struggles:
_____________________________________________________________________________
_____________________________________________________________________________
```

**Week Summary:**
```
AI tool excels at:
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________

AI tool struggles with:
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________

Biggest surprise:
_____________________________________________________________________________
_____________________________________________________________________________

Will you continue using this tool? Why or why not?
_____________________________________________________________________________
_____________________________________________________________________________
```

---

## Scenario 5: Practice "Vibe Code Reviews"

**Goal:** Have AI generate code, then conduct a thorough code review as if a junior developer wrote it.

**Instructions:**
1. Ask AI to generate a simple class or method
2. Review the code thoroughly, looking for issues
3. Document what you find
4. Compare with what you'd look for in human-written code

**Prompt to AI:**
```
Create a Java class called UserValidator that:
- Validates email format using regex
- Validates password strength (8+ chars, uppercase, lowercase, number, special char)
- Validates username (3-20 chars, alphanumeric only)
- Returns helpful error messages for each validation failure
- Includes appropriate unit tests
```

**Generated code review checklist:**

- [ ] **Correctness:** Does the code work as specified?
- [ ] **Edge cases:** Are null, empty, and boundary values handled?
- [ ] **Security:** Any vulnerabilities (regex DoS, injection risks)?
- [ ] **Performance:** Any inefficiencies or potential bottlenecks?
- [ ] **Readability:** Is the code clear and well-organized?
- [ ] **Maintainability:** Easy to modify and extend?
- [ ] **Testing:** Are tests comprehensive and meaningful?
- [ ] **Error handling:** Are errors handled gracefully?
- [ ] **Documentation:** Are comments helpful and accurate?
- [ ] **Best practices:** Follows language/framework conventions?

**Issues found:**
```
1. _________________________________________________________________________
   Severity: [ ] Critical  [ ] Major  [ ] Minor
   Fix: _____________________________________________________________________

2. _________________________________________________________________________
   Severity: [ ] Critical  [ ] Major  [ ] Minor
   Fix: _____________________________________________________________________

3. _________________________________________________________________________
   Severity: [ ] Critical  [ ] Major  [ ] Minor
   Fix: _____________________________________________________________________

(Add more as needed)
```

**Reflection:**
```
Would you approve this code in a PR? Why or why not?
_____________________________________________________________________________
_____________________________________________________________________________

How does reviewing AI code differ from reviewing human code?
_____________________________________________________________________________
_____________________________________________________________________________

What did this exercise teach you about AI's capabilities and limitations?
_____________________________________________________________________________
_____________________________________________________________________________
```

---

## Scenario 6: Code Conversion Challenge

**Goal:** Use AI to convert code between languages and verify the conversion is correct.

**Instructions:**
1. Choose a small piece of code in one language (10-30 lines)
2. Ask AI to convert it to another language
3. Manually verify the conversion is correct
4. Check that it follows target language best practices

**Source language:** ________________________________________________________

**Target language:** ________________________________________________________

**Original code:**
```
[paste here]
```

**Conversion prompt used:**
```
Convert this [source language] code to [target language]:

<source_code>
[paste code]
</source_code>

<requirements>
- Follow [target language] best practices and idioms
- Use [specific framework if applicable]
- Maintain the same functionality and edge case handling
- Add comments explaining any language-specific differences
</requirements>
```

**Converted code:**
```
[paste AI's conversion here]
```

**Verification checklist:**

- [ ] **Functionality:** Does it do the same thing as original?
- [ ] **Idioms:** Uses target language patterns appropriately?
- [ ] **Libraries:** Uses appropriate standard libraries?
- [ ] **Best practices:** Follows target language conventions?
- [ ] **Edge cases:** Handles same edge cases as original?
- [ ] **Performance:** Similar or better performance characteristics?
- [ ] **Type safety:** Appropriate use of type systems?
- [ ] **Error handling:** Errors handled in language-appropriate way?

**Issues found in conversion:**
```
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________
```

**Manual fixes needed:**
```
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________
```

**Key takeaway:**
```
_____________________________________________________________________________
_____________________________________________________________________________
```

---

## Scenario 7: Establish Your AI Usage Guidelines

**Goal:** Define when you will and won't use AI assistance, and how you'll handle different
types of code.

**Instructions:**
1. Reflect on your experiences from the previous scenarios
2. Define your personal AI usage policy
3. Create guidelines for yourself and your team

**My AI Usage Guidelines:**

### When I WILL use AI:
```
1. _________________________________________________________________________
2. _________________________________________________________________________
3. _________________________________________________________________________
4. _________________________________________________________________________
5. _________________________________________________________________________
```

### When I WON'T use AI:
```
1. _________________________________________________________________________
2. _________________________________________________________________________
3. _________________________________________________________________________
4. _________________________________________________________________________
5. _________________________________________________________________________
```

### Code types requiring extra scrutiny:
```
1. _________________________________________________________________________
2. _________________________________________________________________________
3. _________________________________________________________________________
4. _________________________________________________________________________
```

### Handling sensitive/proprietary information:
```
- Never share: ______________________________________________________________
  _____________________________________________________________________________

- Okay to share with precautions: ___________________________________________
  _____________________________________________________________________________

- Precautions I'll take: _____________________________________________________
  _____________________________________________________________________________
```

### My AI-assisted code review checklist:
```
Before submitting AI-generated code for review, I will:

1. [ ] _____________________________________________________________________
2. [ ] _____________________________________________________________________
3. [ ] _____________________________________________________________________
4. [ ] _____________________________________________________________________
5. [ ] _____________________________________________________________________
6. [ ] _____________________________________________________________________
```

### My commitment:
```
I understand that regardless of whether I or AI generated the code, I am responsible
for understanding every line and ensuring quality. I commit to:

1. _________________________________________________________________________
2. _________________________________________________________________________
3. _________________________________________________________________________

Signed: ________________________________  Date: __________________________
```

---

## Bonus Scenario: Teaching with AI

**Goal:** Use AI to create a learning plan for a new technology or concept.

**Sample prompt:**
```
I'm learning [technology/framework] as a [experience level] developer with
experience in [related technologies].

Create a learning plan that:
1. Explains core concepts building on what I already know
2. Provides 3-5 hands-on exercises progressing in difficulty
3. Highlights common pitfalls for developers with my background
4. Recommends specific resources for deeper learning

Focus on practical application rather than theory.
```

**Technology I'm learning:** ________________________________________________

**My learning plan (from AI):**
```
[paste AI's response here]
```

**After completing exercises:**
```
What worked well:
_____________________________________________________________________________
_____________________________________________________________________________

What didn't work:
_____________________________________________________________________________
_____________________________________________________________________________

Additional resources I found helpful:
_____________________________________________________________________________
_____________________________________________________________________________
```

---

## Final Reflection

After completing these scenarios, reflect on your experience:

**Most valuable insight:**
```
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________
```

**Biggest surprise:**
```
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________
```

**How will AI change your development workflow?**
```
_____________________________________________________________________________
_____________________________________________________________________________
_____________________________________________________________________________
```

**Skills you need to develop:**
```
1. _________________________________________________________________________
2. _________________________________________________________________________
3. _________________________________________________________________________
```

**Next steps:**
```
1. _________________________________________________________________________
2. _________________________________________________________________________
3. _________________________________________________________________________
```

---

## Remember

- AI amplifies your existing skills, it doesn't replace them
- Always review and understand AI-generated code
- You're responsible for the code, regardless of who wrote it
- Build your prompt library over time
- Share effective prompts with your team
- Maintain code quality standards
- Keep learning and adapting

**Happy coding with your AI pair programmer!**
