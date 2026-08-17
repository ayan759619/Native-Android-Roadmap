data class Employee(
    val id: Int,
    val name: String,
    val department: String,
    val salary: Int,
    val experienceYears: Int,
    val skills: Set<String>
)

data class EmployeeSummary(
    var name: String,
    var department: String,
    var annualSalary: Int
)

fun main() {
    val employees = listOf(
        Employee(101, "Ayan", "Android", 900000, 5, setOf("Kotlin", "Compose", "Android")),
        Employee(102, "Priya", "iOS", 850000, 4, setOf("Swift", "SwiftUI")),
        Employee(103, "Rahul", "Backend", 1100000, 7, setOf("Kotlin", "Spring", "SQL")),
        Employee(104, "Neha", "QA", 650000, 3, setOf("Testing", "Selenium")),
        Employee(105, "Vikram", "Android", 750000, 2, setOf("Kotlin", "Android")),
        Employee(106, "Sneha", "HR", 600000, 5, setOf("Recruitment", "Excel")),
        Employee(107, "Arjun", "Backend", 1250000, 8, setOf("Java", "Spring", "SQL")),
        Employee(108, "Meera", "QA", 700000, 4, setOf("Testing", "Appium")),
        Employee(109, "Karan", "Android", 1000000, 6, setOf("Kotlin", "Compose", "Coroutines")),
        Employee(110, "Ananya", "Frontend", 800000, 4, setOf("React", "JavaScript")),
        Employee(111, "Rohan", "Backend", 950000, 5, setOf("Kotlin", "Spring")),
        Employee(112, "Isha", "HR", 550000, 2, setOf("Recruitment", "Excel")),
        Employee(113, "Aditya", "Android", 680000, 2, setOf("Kotlin", "Android")),
        Employee(114, "Pooja", "Frontend", 900000, 6, setOf("React", "TypeScript")),
        Employee(115, "Manish", "QA", 780000, 5, setOf("Testing", "Selenium", "API Testing")),
        Employee(116, "Divya", "Backend", 1050000, 6, setOf("Java", "Spring", "SQL")),
        Employee(117, "Nikhil", "Android", 820000, 4, setOf("Kotlin", "Compose")),
        Employee(118, "Kavya", "Frontend", 720000, 3, setOf("React", "JavaScript")),
        Employee(119, "Suresh", "QA", 620000, 2, setOf("Testing")),
        Employee(120, "Riya", "Backend", 1150000, 7, setOf("Kotlin", "Spring", "Docker"))
    )

    println("=".repeat(70))
    println("       KOTLIN COLLECTIONS & SCOPE FUNCTIONS DEMO")
    println("=".repeat(70))

    // map: transform each Employee into a name.
    val employeeNames = employees.map { it.name }
    println("\n--- map ---")
    println(employeeNames.joinToString(", "))

    // filter: select experienced Android/Backend developers.
    val experiencedDevelopers = employees.filter {
        it.experienceYears >= 5 &&
            it.department in setOf("Android", "Backend")
    }
    println("\n--- filter ---")
    experiencedDevelopers.forEach {
        println("${it.name} - ${it.department} - ${it.experienceYears} years")
    }

    // groupBy: organize employees by department.
    val employeesByDepartment = employees.groupBy { it.department }
    println("\n--- groupBy ---")
    employeesByDepartment.toSortedMap().forEach { (department, list) ->
        println("$department: ${list.size} employees")
    }

    // sortedByDescending + take: top five salaries.
    val topEarners = employees
        .sortedByDescending { it.salary }
        .take(5)

    println("\n--- sortedByDescending + take ---")
    topEarners.forEach {
        println("${it.name}: INR ${it.salary}")
    }

    // associate: create employee ID -> name lookup.
    val employeeDirectory = employees.associate { it.id to it.name }

    println("\n--- associate ---")
    println("Employee 107 = ${employeeDirectory[107]}")
    println("Employee 999 = ${employeeDirectory[999] ?: "Not found"}")

    // Set: collect unique skills.
    val allSkills = employees
        .flatMap { it.skills }
        .toSet()

    println("\n--- Set ---")
    println("Unique skills: ${allSkills.sorted().joinToString(", ")}")

    // let: safely process a nullable lookup.
    println("\n--- let ---")
    employeeDirectory[105]?.let { name ->
        println("Employee 105 found: $name")
    }

    // apply: configure and return the same object.
    println("\n--- apply ---")
    val summary = EmployeeSummary("", "", 0).apply {
        name = "Ayan"
        department = "Android"
        annualSalary = 900000
    }
    println("Configured summary: $summary")

    // also: perform a side effect while keeping the collection.
    println("\n--- also ---")
    val experiencedEmployees = employees
        .filter { it.experienceYears >= 3 }
        .also {
            println("Logging: ${it.size} employees have at least 3 years experience")
        }
    println("Result count: ${experiencedEmployees.size}")

    // run: calculate a result using employees as the receiver.
    println("\n--- run ---")
    val averageSalary = employees.run {
        sumOf { it.salary }.toDouble() / size
    }
    println("Average salary: INR ${averageSalary.toInt()}")

    // Meaningful combined pipeline.
    println("\n--- Combined collection pipeline ---")
    val departmentReport = employees
        .filter { it.experienceYears >= 4 }
        .groupBy { it.department }
        .mapValues { (_, list) ->
            list.sortedByDescending { it.salary }.take(2)
        }

    departmentReport.toSortedMap().forEach { (department, list) ->
        println("$department -> ${list.joinToString(", ") { it.name }}")
    }

    println("\n" + "=".repeat(70))
    println("Collections and scope functions demonstrated successfully.")
    println("=".repeat(70))
}