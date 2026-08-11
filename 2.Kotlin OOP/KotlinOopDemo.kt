open class User(
    val id: Int,
    val name: String,
    val email: String
) {
    protected fun basicInfo() = "$name <$email>"

    open fun describe(): String = "User #$id: ${basicInfo()}"
}

interface SkillProvider {
    fun getSkills(): List<String>

    fun hasSkill(skill: String): Boolean =
        getSkills().any { it.equals(skill.trim(), ignoreCase = true) }
}

open class Developer(
    id: Int,
    name: String,
    email: String,
    val experienceYears: Int
) : User(id, name, email), SkillProvider {

    private val skills = mutableListOf<String>()

    fun addSkill(skill: String) {
        val cleanSkill = skill.trim()
        if (cleanSkill.isNotEmpty() && !hasSkill(cleanSkill)) {
            skills.add(cleanSkill)
        }
    }

    override fun getSkills(): List<String> = skills.toList()

    override fun describe(): String {
        val experience = when {
            experienceYears < 0 -> "Invalid experience"
            experienceYears == 0 -> "Fresher"
            experienceYears == 1 -> "1 year"
            else -> "$experienceYears years"
        }
        return "Developer #$id: ${basicInfo()} | Experience: $experience"
    }
}

// Composition: a developer HAS-A profile.
data class DeveloperProfile(
    val githubUsername: String?,
    val location: String?,
    val preferredLanguage: String?
)

class ReactNativeDeveloper(
    id: Int,
    name: String,
    email: String,
    experienceYears: Int,
    val profile: DeveloperProfile
) : Developer(id, name, email, experienceYears) {

    override fun describe() =
        "React Native Developer #$id: ${basicInfo()} | " +
        "Experience: $experienceYears years | " +
        "Location: ${profile.location ?: "Not provided"}"

    fun buildMobileApp() =
        "Building cross-platform mobile apps with React Native"
}

class AndroidDeveloper(
    id: Int,
    name: String,
    email: String,
    experienceYears: Int,
    val profile: DeveloperProfile
) : Developer(id, name, email, experienceYears) {

    override fun describe() =
        "Android Developer #$id: ${basicInfo()} | " +
        "Experience: $experienceYears years | " +
        "Language: ${profile.preferredLanguage ?: "Not specified"}"

    fun buildAndroidApp() = "Building Android apps with Kotlin"
}

object OopDemo {
    fun printDeveloper(developer: Developer) {
        println(developer.describe())
        println(
            "Skills: " +
                developer.getSkills().ifEmpty { listOf("No skills added") }
                    .joinToString(", ")
        )
    }
}

fun main() {
    val user = User(1, "Ayan Sharma", "ayan@example.com")

    val reactNativeProfile = DeveloperProfile(
        githubUsername = "ayan-dev",
        location = "Bengaluru",
        preferredLanguage = "TypeScript"
    )

    val androidProfile = DeveloperProfile(
        githubUsername = null,
        location = null,
        preferredLanguage = "Kotlin"
    )

    val reactNativeDeveloper = ReactNativeDeveloper(
        2, "Priya Singh", "priya@example.com", 4, reactNativeProfile
    )

    val androidDeveloper = AndroidDeveloper(
        3, "Rahul Verma", "rahul@example.com", 2, androidProfile
    )

    reactNativeDeveloper.addSkill("React Native")
    reactNativeDeveloper.addSkill("TypeScript")
    reactNativeDeveloper.addSkill("React Native") // duplicate ignored
    reactNativeDeveloper.addSkill("   ")           // empty ignored

    androidDeveloper.addSkill("Kotlin")
    androidDeveloper.addSkill("Jetpack Compose")
    androidDeveloper.addSkill("Android")

    println("=".repeat(70))
    println("                    KOTLIN OOP DEMO")
    println("=".repeat(70))

    println("\n--- User ---")
    println(user.describe())

    println("\n--- React Native Developer ---")
    OopDemo.printDeveloper(reactNativeDeveloper)
    println(reactNativeDeveloper.buildMobileApp())
    println("Has React Native skill: ${reactNativeDeveloper.hasSkill("react native")}")

    println("\n--- Android Developer ---")
    OopDemo.printDeveloper(androidDeveloper)
    println(androidDeveloper.buildAndroidApp())
    println("Has Kotlin skill: ${androidDeveloper.hasSkill("KOTLIN")}")

    println("\n--- Polymorphism ---")
    val developers: List<Developer> =
        listOf(reactNativeDeveloper, androidDeveloper)
    for (developer in developers) {
        println(developer.describe())
    }

    println("\n--- Interface Contract ---")
    val skillProviders: List<SkillProvider> =
        listOf(reactNativeDeveloper, androidDeveloper)
    for (provider in skillProviders) {
        println(provider.getSkills().joinToString(", "))
    }

    println("\n" + "=".repeat(70))
    println("OOP concepts demonstrated successfully.")
    println("=".repeat(70))
}
