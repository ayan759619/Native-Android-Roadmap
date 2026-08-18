/*
 * Kotlin Advanced - API Result & DTO -> UI Model
 *
 * Concepts demonstrated:
 * - sealed class
 * - data classes
 * - enum class
 * - extension functions
 * - lambdas
 * - higher-order functions
 * - DTO / UI model separation
 * - null safety
 */

// ============================================================
// DATA / API LAYER
// ============================================================

data class UserDto(
    val id: Int,
    val fullName: String?,
    val email: String?,
    val role: String?,
    val isActive: Boolean?
)

// ============================================================
// UI / DOMAIN LAYER
// ============================================================

enum class UserStatus {
    ACTIVE,
    INACTIVE,
    UNKNOWN
}

data class UserUiModel(
    val id: Int,
    val displayName: String,
    val email: String,
    val role: String,
    val status: UserStatus
)

// ============================================================
// EXTENSION FUNCTION
// DTO -> UI MODEL
// ============================================================

fun UserDto.toUiModel(): UserUiModel {

    val displayName =
        fullName
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?: "Unknown User"

    val safeEmail =
        email
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?: "Email not available"

    val safeRole =
        role
            ?.trim()
            ?.takeIf { it.isNotEmpty() }
            ?: "User"

    val status = when (isActive) {
        true -> UserStatus.ACTIVE
        false -> UserStatus.INACTIVE
        null -> UserStatus.UNKNOWN
    }

    return UserUiModel(
        id = id,
        displayName = displayName,
        email = safeEmail,
        role = safeRole,
        status = status
    )
}

// ============================================================
// SEALED CLASS
// ============================================================

sealed class ApiResult<out T> {

    data object Loading : ApiResult<Nothing>()

    data class Success<T>(
        val data: T
    ) : ApiResult<T>()

    data class Error(
        val message: String,
        val code: Int? = null
    ) : ApiResult<Nothing>()
}

// ============================================================
// HIGHER-ORDER FUNCTION
// ============================================================

fun printUsers(
    users: List<UserUiModel>,
    formatter: (UserUiModel) -> String
) {
    users.forEach { user ->
        println(formatter(user))
    }
}

// ============================================================
// GENERIC HIGHER-ORDER FUNCTION
// ============================================================

fun <T, R> transformItems(
    items: List<T>,
    transform: (T) -> R
): List<R> {

    return items.map(transform)
}

// ============================================================
// HANDLE API RESULT
// ============================================================

fun renderResult(
    result: ApiResult<List<UserDto>>
) {

    when (result) {

        // -----------------------------
        // LOADING
        // -----------------------------
        ApiResult.Loading -> {

            println("Loading users...")
        }

        // -----------------------------
        // SUCCESS
        // -----------------------------
        is ApiResult.Success -> {

            val uiUsers = transformItems(result.data) { dto ->

                dto.toUiModel()
            }

            println("Users loaded successfully: ${uiUsers.size}")

            printUsers(uiUsers) { user ->

                "ID=${user.id} | " +
                        "${user.displayName} | " +
                        "${user.email} | " +
                        "${user.role} | " +
                        "${user.status}"
            }
        }

        // -----------------------------
        // ERROR
        // -----------------------------
        is ApiResult.Error -> {

            val codeText =
                result.code?.let {
                    " (code: $it)"
                } ?: ""

            println(
                "Error: ${result.message}$codeText"
            )
        }
    }
}

// ============================================================
// MAIN
// ============================================================

fun main() {

    // ========================================================
    // SAMPLE API RESPONSE
    // ========================================================

    val dtoUsers = listOf(

        UserDto(
            id = 101,
            fullName = "Ayan Sharma",
            email = "ayan@example.com",
            role = "Developer",
            isActive = true
        ),

        UserDto(
            id = 102,
            fullName = null,
            email = null,
            role = "Android Developer",
            isActive = null
        ),

        UserDto(
            id = 103,
            fullName = "  Priya Singh  ",
            email = "priya@example.com",
            role = null,
            isActive = false
        )
    )

    // ========================================================
    // HEADER
    // ========================================================

    println("=".repeat(70))

    println(
        "              KOTLIN ADVANCED API RESULT DEMO"
    )

    println("=".repeat(70))

    // ========================================================
    // LOADING STATE
    // ========================================================

    println("\n--- Loading ---")

    renderResult(
        ApiResult.Loading
    )

    // ========================================================
    // SUCCESS STATE
    // ========================================================

    println("\n--- Success ---")

    renderResult(
        ApiResult.Success(dtoUsers)
    )

    // ========================================================
    // ERROR STATE
    // ========================================================

    println("\n--- Error ---")

    renderResult(
        ApiResult.Error(
            message = "Unable to fetch users",
            code = 500
        )
    )

    // ========================================================
    // LAMBDA EXAMPLE
    // ========================================================

    val statusFormatter: (UserUiModel) -> String = { user ->

        "${user.displayName}: ${user.status}"
    }

    println("\n--- Lambda ---")

    dtoUsers
        .map { it.toUiModel() }
        .forEach { user ->

            // forEach requires Unit.
            // println() returns Unit.
            println(statusFormatter(user))
        }

    // ========================================================
    // ANOTHER HIGHER-ORDER FUNCTION EXAMPLE
    // ========================================================

    println("\n--- Higher-Order Function ---")

    val uiUsers = transformItems(dtoUsers) { dto ->

        dto.toUiModel()
    }

    printUsers(uiUsers) { user ->

        "${user.displayName} -> ${user.role}"
    }

    // ========================================================
    // FINAL MESSAGE
    // ========================================================

    println("\n" + "=".repeat(70))

    println(
        "Advanced Kotlin concepts demonstrated successfully."
    )

    println("=".repeat(70))
}