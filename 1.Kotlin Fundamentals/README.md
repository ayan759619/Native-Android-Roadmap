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


