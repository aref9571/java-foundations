# java-foundations

[![Maven verification](https://github.com/aref9571/java-foundations/actions/workflows/maven.yml/badge.svg)](https://github.com/aref9571/java-foundations/actions/workflows/maven.yml)

A day-by-day study of core Java as a foundation for backend engineering. Every topic is practiced on one small domain, JobTrackr, which tracks job applications from the first application to an offer or a rejection.

The code is plain Java 21 with no frameworks. Each day lives in its own `dayXX` package with matching unit tests, so the repository can be read in the order the topics were learned.

## Contents

- [Topics covered](#topics-covered)
- [The JobTrackr domain](#the-jobtrackr-domain)
- [Tech stack](#tech-stack)
- [Getting started](#getting-started)
- [Console programs](#console-programs)
- [Project structure](#project-structure)
- [Progress](#progress)
- [Next step: JobTrackr API](#next-step-jobtrackr-api)

## Topics covered

| Area | Topics |
|---|---|
| Language basics | Methods and their contracts, `if` and `switch` rules, arrays and simple algorithms |
| Domain modeling | Value objects, validation, immutability, state transitions, custom exceptions |
| Collections and generics | `List`, `Set` and `Map`, defensive copies, generic containers and stores |
| Application structure | In-memory repository, service layer, console programs |
| Persistence | File-based storage with escaping and safe loading |
| Functional style | Streams for queries and statistics, `Optional` for lookups that can be empty |
| Concurrency | Threads, executors, race conditions, `AtomicInteger` versus `synchronized`, scheduled background refresh with immutable snapshots |
| Testing | JUnit 5 unit tests for every day, including error paths and concurrent code |

## The JobTrackr domain

- `JobApplication` has a company, a role, an applied date, an optional expected salary and a status. The company and role must not be blank, and the applied date must not be in the future.
- `ApplicationStatus` is one of `APPLIED`, `INTERVIEWING`, `OFFER` or `REJECTED`. An application can move from `APPLIED` to `INTERVIEWING` or `REJECTED`, and from `INTERVIEWING` to `OFFER` or `REJECTED`. `OFFER` and `REJECTED` are final. Any other change throws `InvalidApplicationStateException`.
- `Money` stores an amount in cents with an ISO 4217 currency code, and only adds or subtracts amounts in the same currency.
- `ApplicationRepository` rejects a second application with the same company and role, ignoring case and surrounding spaces.

## Tech stack

- Java 21
- Maven
- JUnit 5
- GitHub Actions

## Getting started

### Requirements

- JDK 21
- Maven 3.9 or newer

### Build and run the tests

From the project root:

```bash
mvn --batch-mode verify
```

This compiles the code and runs all 302 tests. GitHub Actions runs the same command on every push.

## Console programs

Compile once, then run any class that has a `main` method:

```bash
mvn --batch-mode compile
java -cp target/classes day16.JobTrackerCli
```

| Class | What it does |
|---|---|
| `day07.ConsoleJobTracker` | Interactive job tracker menu |
| `day08_MiniProject_TaskManager.ConsoleTaskManager` | Interactive task manager menu |
| `day15.Day15DebugScenario` | Saves and loads sample data in `Debug.csv` with log output |
| `day16.JobTrackerCli` | Loads `jobtracker-data.txt` if present, adds or updates a sample application, saves the file and prints a summary |
| `day19.QueryCli` | Prints recent, backend and stuck applications from sample data |
| `day21.ExecutorExample` | Runs a task on an `ExecutorService` and waits for the result |
| `day23.CounterPerformance` | Compares the two counter implementations |
| `day26.AnalyticsCli` | Prints a refreshed analytics snapshot three times |

`Debug.csv` and `jobtracker-data.txt` are written to the project root when those programs run, and `QueryCli` reads `applications.csv` from there if it exists. These data files are not tracked by Git.

## Project structure

```text
src/main/java/dayXX    production code, one package per day
src/test/java/dayXX    unit tests for that day's code
.github/workflows      Maven verification workflow
```

- The JobTrackr domain model (`JobApplication`, `ApplicationStatus`, `Money`) is in `day05`.
- Storage is in `day10` (`ApplicationRepository`, in memory) and `day14` (`FileApplicationStore`, file based).
- The use cases are in `day13` (`ApplicationService`).

## Progress

All days below are complete, and their code and tests are on `main`.

| Day | Topic | What was built |
|---|---|---|
| 1 | Project setup | Java 21 and Maven project with JUnit 5 |
| 2 | Methods and unit tests | `MathToolkit` and `StringToolkit` with method contracts and arrange, act, assert tests |
| 3 | Business rules and priorities | `ApplicationRules` with `if` and short-circuit logic, `ApplicationPriorityCalculator` with `switch` |
| 4 | Arrays and basic algorithms | `ArrayStats` (min, max, sum, average) and `ArrayUtils` (copy, reverse, search, safe access) |
| 5 | Domain modeling and immutability | `Money`, `JobApplication`, `InterviewSlot` and `JobApplicationBuilder` with validation |
| 6 | Refactoring and structure | Extracted validation and helper methods and less duplication in the earlier days' code |
| 7 | Console job tracker | `ConsoleJobTracker` on top of `JobTrackerService` to add, list, search and update applications |
| 8 | Task manager mini-project | `ConsoleTaskManager` and `TaskService` to add, list, search, update the status of and delete tasks |
| 9 | Collections and tagging | `ApplicationTags` and `ApplicationIndex` with `computeIfAbsent`, unmodifiable collections and defensive copies |
| 10 | In-memory repository | `ApplicationRepository` and `ApplicationQueries` with the duplicate rule |
| 11 | Custom exceptions and state transitions | `ApplicationException` and its subclasses, immutable `withStatus` transitions, `findByIdOrThrow` |
| 12 | Generics | `Box`, `Pair`, `CollectionUtils`, and a generic `InMemoryStore` interface with `ListInMemoryStore` |
| 13 | Service layer | `ApplicationService` for creating, querying and updating applications |
| 14 | File I/O and persistence | `JobApplicationCsvConverter` and `FileApplicationStore`: one line per application, `\|`-separated fields, `\|`, `\` and line breaks escaped, blank lines skipped |
| 15 | Debugging and logging | `Day15DebugScenario` traces the flow through service, repository and file store |
| 16 | Command-line program | `JobTrackerCli` loads data from a file, runs operations through the service, saves and prints a summary |
| 17 | Streams | `StreamBasics` and `ApplicationStreamQueries` for filtering, mapping, sorting, distinct values and counting |
| 18 | Stream statistics | `ApplicationStatistics` with counts by status and company, active and final counts |
| 19 | Query service | `ApplicationQueryService` and `QueryCli` for recent applications, role keyword search and stuck applications (applied more than N days ago and still active) |
| 20 | Optional | Lookups return `Optional<JobApplication>`, `findFirstByCompany` and `findMostRecentActive`, `OptionalUtils` helpers |
| 21 | Threads and executors | `Thread`, `Runnable` and `join()`, `ExecutorService` with `Callable` and `Future`, a race condition next to a safe `AtomicInteger` version |
| 22 | Immutable snapshots and parallel statistics | `ConcurrencyPatterns` and `ApplicationStatsParallel` with a fixed thread pool over defensive copies |
| 23 | Contention benchmark | `CounterPerformance` compares `AtomicInteger` and `synchronized` counters across thread counts |
| 24 | Concurrent counters and background statistics | `RequestCounter` implementations with concurrency tests, `StatsRefresher` with a `ScheduledExecutorService` and a volatile immutable snapshot |
| 26 | Analytics snapshot | `AnalyticsService`, `AnalyticsSnapshot` (totals, counts by status, top companies), `AnalyticsRefresher`, `SimpleLogger` and `ConsoleLogger`, `AnalyticsCli` |

Days 1 and 6 have no package of their own: Day 1 set up the project, and Day 6 changed the code of earlier days. There is no Day 25 package.

After Day 26 the whole repository was reviewed. The review fixed integer overflow in `ArrayStats`, percent signs breaking `ConsoleLogger`, duplicate detection with surrounding spaces, blank lines in data files, `ConsoleJobTracker` stopping on invalid input, and unknown currency codes in `Money`, each with a regression test. It also added missing tests, removed dead code and stopped tracking IDE and runtime data files.

## Next step: JobTrackr API

The JobTrackr domain continues in [jobtrackr-api](https://github.com/aref9571/jobtrackr-api), a Spring Boot REST API with PostgreSQL. It reuses the status rules, the validation, the duplicate rule and the exceptions from this repository.

Completed so far in jobtrackr-api:

| Step | What was built |
|---|---|
| Project setup | Spring Boot 4.1.1 project with the Maven Wrapper, a health check endpoint and a GitHub Actions build |
| Domain model | `JobApplication` and `ApplicationStatus` with the status rules from this repository, validation and the three exceptions |
| PostgreSQL, Flyway and JPA | The `job_applications` table created by a Flyway migration, a Spring Data repository and tests against PostgreSQL in Testcontainers |
| Service layer and DTOs | `ApplicationService` with create, read, update, status change and delete, request and response records, Mockito unit tests |

The REST endpoints for job applications are the next part of that project.
