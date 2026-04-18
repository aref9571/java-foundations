# java-foundations

Learning core Java for backend and cloud engineering.

## Daily progress

- Day 1 – Project setup: Java 21 + Maven, JUnit 5, basic test runner with mvn test.
- Day 2 – Method contracts + unit tests: math and string toolkits with pure functions and AAA-style tests.
- Day 3 – Business rules + priorities: job application rules with if/short-circuiting and a priority calculator using switch.
- Day 4 – Arrays + basic algorithms: ArrayStats (min/max/sum/average) and ArrayUtils (copy/reverse/search/safeGet) with JUnit tests.
- Day 5 – Domain modeling + immutability: Money value object, JobApplication and InterviewSlot entities with validation and JUnit tests.
- Day 6 – Refactoring + structure: cleaned up packages, extracted validation and helper methods, reduced duplication in rules, domain objects, and array utilities.
- Day 8 – Implemented ApplicationTags and ApplicationIndex; practiced List/Set/Map design, computeIfAbsent, unmodifiable collections, defensive copies, and null-safe collection returns.
For more detailed notes per day, see docs/ (planned).
- Day 9 - in-memory repository and query utilities for JobApplication.
- Day 10 – Custom domain exceptions, JobApplication state transitions (immutable withStatus), and repository fail-fast error handling (findByIdOrThrow, duplicate checks, status updates) with focused tests on exception paths.
- Day 11 - Generics and reusable infrastructure: Implemented generic containers (Box, Pair), collection utilities, and an in-memory generic store to practice type-safe reusable code.
- Day 12 - Added ApplicationService on top of ApplicationRepository to handle job application use cases (create, query, update status) with tests.
- Day 13 – Testing power-up: added a JobApplication test data builder, made tests deterministic, and used JUnit 5 parameterized tests to cover business rules with fewer, cleaner tests.
Day 14 – File I/O and persistence: added a CSV converter and a file-backed store for JobApplication, with tests that verify saving and loading applications from disk.
- Day 15 – Debugging and logging: added a Day15DebugScenario to exercise JobTrackr end-to-end, practiced stepping through service/repository/file-store code with the debugger, and introduced structured INFO/DEBUG/ERROR-style log messages to trace application flow.

## How to run tests

From the project root:

```bash
mvn test
