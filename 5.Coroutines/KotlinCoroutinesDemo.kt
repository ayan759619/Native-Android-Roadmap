import kotlinx.coroutines.*

data class ApiData(
    val user: String,
    val account: String
)

suspend fun fetchUserApi(): String {
    println("[User API] Started on ${Thread.currentThread().name}")
    delay(1500)
    println("[User API] Completed")
    return "Ayan Sharma"
}

suspend fun fetchAccountApi(): String {
    println("[Account API] Started on ${Thread.currentThread().name}")
    delay(2000)
    println("[Account API] Completed")
    return "Premium Account"
}

suspend fun fetchWithException(): String {
    println("[Error API] Started")
    delay(700)
    throw IllegalStateException("Simulated API failure")
}

suspend fun fetchCombinedResult(): ApiData = coroutineScope {
    val userDeferred = async(Dispatchers.IO) {
        fetchUserApi()
    }

    val accountDeferred = async(Dispatchers.IO) {
        fetchAccountApi()
    }

    ApiData(
        user = userDeferred.await(),
        account = accountDeferred.await()
    )
}

fun cancellationDemo() = runBlocking {
    println("\n--- Cancellation Demo ---")

    val job = launch(Dispatchers.Default) {
        try {
            repeat(10) { index ->
                ensureActive()
                println("Long-running task: step ${index + 1}")
                delay(400)
            }
        } catch (exception: CancellationException) {
            println("Task cancelled correctly.")
            throw exception
        } finally {
            println("Cancellation cleanup executed.")
        }
    }

    delay(1100)
    println("Requesting cancellation...")
    job.cancelAndJoin()
    println("Job isCancelled = ${job.isCancelled}")
}

fun exceptionDemo() = runBlocking {
    println("\n--- Exception Handling Demo ---")

    try {
        val result = coroutineScope {
            async(Dispatchers.IO) {
                fetchWithException()
            }.await()
        }

        println(result)
    } catch (exception: IllegalStateException) {
        println("Handled API exception: ${exception.message}")
    }
}

fun main() = runBlocking {
    println("=".repeat(70))
    println("              KOTLIN COROUTINES DEMO")
    println("=".repeat(70))

    println("\n--- Concurrent API Calls ---")

    val startTime = System.currentTimeMillis()

    // coroutineScope provides structured concurrency.
    // async starts both API calls without blocking the current thread.
    val combinedResult = fetchCombinedResult()

    val elapsed = System.currentTimeMillis() - startTime

    println(
        "Combined Result: user=${combinedResult.user}, " +
            "account=${combinedResult.account}"
    )
    println("Completed in approximately ${elapsed} ms")
    println("The calls run concurrently, so total time is close to the slower call.")

    exceptionDemo()
    cancellationDemo()

    println("\n--- launch Demo ---")

    val loggingJob = launch(Dispatchers.Default) {
        repeat(3) { index ->
            delay(200)
            println("launch coroutine log ${index + 1}")
        }
    }

    loggingJob.join()

    println("\n" + "=".repeat(70))
    println("Coroutines concepts demonstrated successfully.")
    println("=".repeat(70))
}