data class Employee(
    val id: Int,
    val name: String,
    val email: String?,
    val department: String?,
    var yearsOfExperience: Int?
)

fun experienceCategory(years: Int?): String {
    return when {
        years == null -> "Experience not provided"
        years < 0 -> "Invalid experience"
        years == 0 -> "Fresher"
        years <= 2 -> "Junior"
        years <= 5 -> "Mid-Level"
        else -> "Senior"
    }
}

fun formatEmployee(employee: Employee): String {
    val email = employee.email ?: "Not provided"
    val department = employee.department ?: "Not assigned"
    val experience = employee.yearsOfExperience?.let { "$it years" } ?: "Not provided"
    val category = experienceCategory(employee.yearsOfExperience)

    return """
        |ID           : ${employee.id}
        |Name         : ${employee.name}
        |Email        : $email
        |Department   : $department
        |Experience   : $experience
        |Category     : $category
        """.trimMargin()
}

fun printEmployees(employees: List<Employee>) {
    println("=".repeat(60))
    println("                 EMPLOYEE DIRECTORY")
    println("=".repeat(60))

    for ((index, employee) in employees.withIndex()) {
        println("\nEmployee ${index + 1}")
        println("-".repeat(60))
        println(formatEmployee(employee))
    }

    println("\n" + "=".repeat(60))
    println("Total employees: ${employees.size}")
    println("=".repeat(60))
}

fun main() {
    val employees = listOf(
        Employee(101, "Ayan Sharma", "ayan@example.com", "Engineering", 6),
        Employee(102, "Priya Singh", null, "QA", 2),
        Employee(103, "Rahul Verma", "rahul@example.com", null, null),
        Employee(104, "Neha Patel", null, null, 0)
    )

    printEmployees(employees)

    // Demonstrates mutable `var`.
    var activeEmployeeCount = employees.size
    activeEmployeeCount += 1
    println("\nActive employee count after onboarding: $activeEmployeeCount")

    // Demonstrates nullable-safe access.
    val firstEmployeeEmail = employees.firstOrNull()?.email
    if (firstEmployeeEmail != null) {
        println("First employee email: $firstEmployeeEmail")
    } else {
        println("First employee email: Not provided")
    }
}
