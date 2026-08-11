# Kotlin OOP Demo

## Assignment
Build `User`, `Developer`, `ReactNativeDeveloper` and `AndroidDeveloper`.
Use an interface for a skill contract and demonstrate inheritance and composition.

## Covered
- Classes and primary constructors
- `open`, inheritance and method overriding
- Interface (`SkillProvider`)
- Visibility: `private` and `protected`
- Composition through `DeveloperProfile`
- Polymorphism
- Kotlin nullable fields
- Edge cases: empty/duplicate skills and missing profile values

## Design
`User -> Developer -> ReactNativeDeveloper / AndroidDeveloper`

Inheritance is used only for real **is-a** relationships.

`DeveloperProfile` is composition because a developer **has a profile**; a profile
is not a type of developer.

`SkillProvider` is an interface because it defines a behavior contract.

## Build and run

```bash
kotlinc KotlinOopDemo.kt -include-runtime -d kotlin-oop-demo.jar
java -jar kotlin-oop-demo.jar
```

