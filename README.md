# java-foundations

Learning core Java as a foundation for backend and cloud engineering, using a small "JobTrackr" domain to tie everything together.

## Daily progress

- **Day 1 - Project setup**
  Java 21 + Maven, JUnit 5, first tests, and running the test suite with Maven.

- **Day 2 - Methods and unit tests**
  Pure math and string toolkits, method contracts, and AAA-style unit tests.

- **Day 3 - Business rules and priorities**
  Job application rules using `if` and short-circuiting, plus an application priority calculator with `switch`.

- **Day 4 - Arrays and basic algorithms**
  `ArrayStats` (min, max, sum, average) and `ArrayUtils` (copy, reverse, search, safe access) with JUnit coverage.

- **Day 5 - Domain modeling and immutability**
  `Money` value object (amount in cents and an ISO 4217 currency code), `JobApplication` and `InterviewSlot` domain entities with validation and tests.

- **Day 6 - Refactoring and structure**
  Cleaner packages, extracted validation and helper methods, reduced duplication in rules, domain objects, and array utilities.

- **Day 7 - ConsoleJobTracker mini-project**
  Console-based job tracker (`ConsoleJobTracker`) on top of `JobTrackerService` to add, list, search, and update applications.

- **Day 8 - Task manager mini-project**
  Console task manager covering CRUD-style flows, input validation, and task status updates.

- **Day 9 - Collections and tagging**
  `ApplicationTags` and `ApplicationIndex`; practiced `List` / `Set` / `Map`, `computeIfAbsent`, unmodifiable collections, defensive copies, and null-safe collection returns.

- **Day 10 - In-memory repository**
  `ApplicationRepository` and query utilities for `JobApplication` to model an in-memory persistence layer. The same company and role (ignoring case and surrounding spaces) cannot be added twice.

- **Day 11 - Custom exceptions and state transitions**
  Domain-specific exceptions, immutable state transitions (`withStatus`), and repository fail-fast behavior (`findByIdOrThrow`, duplicate checks, status updates) with focused tests for error paths.

- **Day 12 - Generics and reusable infrastructure**
  Generic containers (`Box`, `Pair`), collection utility helpers, and an in-memory generic store to practice type-safe reusable code.

- **Day 13 - Service layer**
  `ApplicationService` on top of `ApplicationRepository` to handle job application use cases (create, query, update status) with validation and tests.

- **Day 14 - File I/O and persistence**
  `JobApplicationCsvConverter` and `FileApplicationStore` for a file-backed `JobApplication` store. Each application is one line with `|`-separated fields; `|`, `\` and line breaks inside values are escaped, and blank lines are skipped when loading.

- **Day 15 - Debugging and logging mindset**
  `Day15DebugScenario` to exercise JobTrackr end-to-end, debugging through service, repository, and file store, with INFO/DEBUG-style messages to trace application flow.

- **Day 16 - CLI and consolidation**
  `JobTrackerCli` entry point that loads data from file, uses the service layer to perform operations, saves back to disk, and prints a small summary.

- **Day 17 - Stream-based collection processing**
  `ApplicationStreamQueries` to filter applications by company and status, list unique sorted company names, and count by status.

- **Day 18 - Stream-based statistics**
  `ApplicationStatistics` with stream aggregations (status, company, active and final counts), summary methods in `ApplicationService`, and a statistics summary in `JobTrackerCli`.

- **Day 19 - Query service and read-side views**
  `ApplicationQueryService` with queries for recent applications, role keyword search, and stuck applications (applied more than N days ago and still active). `QueryCli` seeds sample data, also loads `applications.csv` if it exists, and prints the three views.

- **Day 20 - Optionals and null-safe queries**
  Repository and service lookups return `Optional<JobApplication>` instead of null, with `findFirstByCompany` and `findMostRecentActive` built on `filter`, `findFirst`, and `max`, plus `OptionalUtils` helpers.

- **Day 21 - Threads, executors, and race conditions**
  Basic `Thread` and `Runnable` usage with `join()`, `ExecutorService` with `Callable` and `Future`, and a race condition on shared state next to a safe version using `AtomicInteger`.

- **Day 22 - Immutable snapshots and parallel statistics**
  Avoiding shared mutable state: `ExecutorService`-based processing over immutable snapshots, and parallel JobTrackr statistics (`ApplicationStatsParallel`) using a fixed thread pool and defensive copies.

- **Day 23 - AtomicInteger vs synchronized under contention**
  `CounterPerformance` benchmark comparing `AtomicInteger`- and `synchronized`-based counters across different thread counts.

- **Day 24 - Concurrent counters and background stats**
  `RequestCounter` with `AtomicInteger`- and `synchronized`-based implementations plus concurrency tests, and `StatsRefresher`, which uses a `ScheduledExecutorService` and a volatile immutable snapshot to keep statistics up to date in the background.

- **Day 26 - Analytics snapshot**
  `AnalyticsService` builds an immutable `AnalyticsSnapshot` (totals, counts by status, top companies), `AnalyticsRefresher` refreshes it on a schedule, and `SimpleLogger` / `ConsoleLogger` provide `{}`-style log messages. `AnalyticsCli` prints the snapshot a few times.

## Project structure

- `src/main/java` - production code organized by `dayXX` packages (each day focuses on a core Java / backend concept).
- `src/test/java` - matching test packages with unit tests for each day's code.
- JobTrackr domain - centered around `JobApplication`, `ApplicationStatus`, and `Money` in `day05`.
- Infrastructure - in-memory repository (`ApplicationRepository`), file-backed store (`FileApplicationStore`), and utility classes (`JobApplicationCsvConverter`, generic stores).

## Requirements

- Java 21
- Maven 3.9 or newer

## How to build and run the tests

From the project root:

```bash
mvn --batch-mode verify
```

This is the same command the GitHub Actions workflow runs on every push.

## How to run the console programs

Compile once, then run any of the classes with a `main` method:

```bash
mvn --batch-mode compile
java -cp target/classes day16.JobTrackerCli
```

| Class | What it does |
|---|---|
| `day07.ConsoleJobTracker` | Interactive job tracker menu |
| `day08_MiniProject_TaskManager.ConsoleTaskManager` | Interactive task manager menu |
| `day15.Day15DebugScenario` | Saves and loads sample data in `Debug.csv` with log output |
| `day16.JobTrackerCli` | Loads `jobtracker-data.txt` if present, adds or updates a sample application, saves the file, and prints a summary |
| `day19.QueryCli` | Prints recent, backend, and stuck applications from sample data |
| `day21.ExecutorExample` | Runs a task on an `ExecutorService` and waits for the result |
| `day23.CounterPerformance` | Compares the two counter implementations |
| `day26.AnalyticsCli` | Prints a refreshed analytics snapshot three times |

`Debug.csv` and `jobtracker-data.txt` are written to the project root when those programs run, and `QueryCli` reads `applications.csv` from there if it exists. These data files are not tracked by Git.
