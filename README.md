# java-foundations

Learning core Java as a foundation for backend and cloud engineering, using a small “JobTrackr” domain to tie everything together.

## Daily progress (Month 1)

- **Day 1 – Project setup**  
  Java 21 + Maven, JUnit 5, first tests, and running the test suite with `mvn test`.

- **Day 2 – Methods and unit tests**  
  Pure math and string toolkits, method contracts, and AAA-style unit tests.

- **Day 3 – Business rules and priorities**  
  Job application rules using `if` and short-circuiting, plus an application priority calculator with `switch`.

- **Day 4 – Arrays and basic algorithms**  
  `ArrayStats` (min, max, sum, average) and `ArrayUtils` (copy, reverse, search, safe access) with JUnit coverage.

- **Day 5 – Domain modeling and immutability**  
  `Money` value object, `JobApplication` and `InterviewSlot` domain entities with validation and tests.

- **Day 6 – Refactoring and structure**  
  Cleaner packages, extracted validation and helper methods, reduced duplication in rules, domain objects, and array utilities.

- **Day 7 – ConsoleJobTracker mini-project**  
  Simple console-based job tracker that uses the domain model and basic services to drive the JobTrackr flow.

- **Day 8 – Collections and tagging**  
  `ApplicationTags` and `ApplicationIndex`; practiced `List` / `Set` / `Map`, `computeIfAbsent`, unmodifiable collections, defensive copies, and null-safe collection returns.

- **Day 9 – In-memory repository**  
  `ApplicationRepository` and query utilities for `JobApplication` to model an in-memory persistence layer.

- **Day 10 – Custom exceptions and state transitions**  
  Domain-specific exceptions, immutable state transitions (`withStatus`), and repository fail-fast behavior (`findByIdOrThrow`, duplicate checks, status updates) with focused tests for error paths.

- **Day 11 – Generics and reusable infrastructure**  
  Generic containers (`Box`, `Pair`), collection utility helpers, and an in-memory generic store to practice type-safe reusable code.

- **Day 12 – Service layer**  
  `ApplicationService` on top of `ApplicationRepository` to handle job application use cases (create, query, update status) with validation and tests.

- **Day 13 – Testing power-up**  
  `JobApplication` test data builder, more deterministic tests, and JUnit 5 parameterized tests to cover business rules with fewer, clearer test methods.

- **Day 14 – File I/O and persistence**  
  CSV converter and `FileApplicationStore` for a file-backed `JobApplication` store, with tests that verify saving and loading from disk.

- **Day 15 – Debugging and logging mindset**  
  `Day15DebugScenario` to exercise JobTrackr end-to-end, debugging through service/repository/file-store, and structured INFO/DEBUG-style messages to trace application flow.

- **Day 16 – CLI and consolidation**  
  `JobTrackrCli` entry point that loads data from file, uses the service layer to perform operations, saves back to disk, and prints a small summary. This ties together domain, repository, service, persistence, and logging into a single runnable demo.

## Project structure

- `src/main/java` – production code organized by `day0x` packages (each day focuses on a core Java / backend concept).
- `src/test/java` – matching test packages with unit and integration-style tests for each day’s code.
- `JobTrackr` domain – centered around `JobApplication`, `ApplicationStatus`, and related types.
- Infrastructure – in-memory repository (`ApplicationRepository`), file-backed store (`FileApplicationStore`), and utility classes (`JobApplicationCsvConverter`, generic stores, etc.).
- Entry point – `JobTrackrCli` (Day 16) for a minimal CLI demo of the full backend slice.

## How to run tests

From the project root:

```bash
mvn test
```

## How to run the CLI demo

From the project root, run the Day 16 CLI entry point (for example from your IDE, or using your build tool’s run configuration). It will:

- Load existing applications from `jobtrackr-data.txt` (if present).
- Use `ApplicationService` to create and update applications.
- Save the updated list back to the file.
- Print a short summary of total and active applications to the console.