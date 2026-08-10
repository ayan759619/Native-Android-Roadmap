# Kotlin Employee Console Program

A runnable Kotlin console application covering the requested beginner Kotlin concepts.

## Covered concepts

- `val` / `var`
- Kotlin types: `Int`, `String`, nullable types and collections
- `data class` model
- Functions and return types
- Null safety: `?`, `?:`, `?.`, `let`
- Conditions: `if`, `when`
- Loops: `for`
- Collections: `List`
- Edge cases: missing email/department/experience, zero experience, invalid negative experience
- Readable naming and formatted output

## Run

```bash
java -jar employee-console.jar
```

## Build from source

```bash
kotlinc src/main/kotlin/EmployeeConsole.kt -include-runtime -d employee-console.jar
java -jar employee-console.jar
```

## Experience rules

| Years | Category |
|---:|---|
| null | Experience not provided |
| < 0 | Invalid experience |
| 0 | Fresher |
| 1-2 | Junior |
| 3-5 | Mid-Level |
| > 5 | Senior |

## Architecture / explanation

- `Employee`: data model.
- `experienceCategory()`: business rule.
- `formatEmployee()`: formatting responsibility.
- `printEmployees()`: iteration/output responsibility.
- `main()`: sample data and demonstrations.

## Git submission

```bash
git init
git add .
git commit -m "Create Kotlin employee console program"
git branch -M main
```

Push the repository and create a PR. The included `demo-output.txt` can be used as short demo evidence.
