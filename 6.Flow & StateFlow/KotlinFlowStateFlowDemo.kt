import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

data class UserUiState(
    val userName: String = "Loading...",
    val counter: Int = 0,
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class UserRepository {
    // Cold Flow: producer starts when collect() is called.
    fun userUpdates(): Flow<String> = flow {
        listOf("Ayan", "Ayan Sharma", "Ayan Sharma - Active")
            .forEach { name ->
                delay(500)
                println("[Repository] Emitting: $name")
                emit(name)
            }
    }

    fun counterUpdates(): Flow<Int> = flow {
        repeat(5) { value ->
            delay(400)
            emit(value + 1)
        }
    }
}

class UserViewModelLike(
    private val repository: UserRepository,
    private val scope: CoroutineScope
) {
    // Convert cold Flow to StateFlow with lifecycle-aware sharing.
    val counterState: StateFlow<Int> =
        repository.counterUpdates().stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = 0
        )

    // Backing property protects mutable UI state.
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    // SharedFlow is used for one-time events/broadcasts.
    private val _events = MutableSharedFlow<String>(
        replay = 0,
        extraBufferCapacity = 1
    )
    val events: SharedFlow<String> = _events.asSharedFlow()

    fun observeUserUpdates(): Job = scope.launch {
        repository.userUpdates().collect { userName ->
            _uiState.update {
                it.copy(
                    userName = userName,
                    counter = it.counter + 1,
                    isLoading = false,
                    errorMessage = null
                )
            }
            _events.emit("User updated: $userName")
        }
    }

    fun showError(message: String) {
        scope.launch {
            _uiState.update {
                it.copy(isLoading = false, errorMessage = message)
            }
            _events.emit("Error event: $message")
        }
    }
}

fun main() = runBlocking {
    println("=".repeat(70))
    println("              KOTLIN FLOW & STATEFLOW DEMO")
    println("=".repeat(70))

    val repository = UserRepository()

    // Simulated ViewModel lifecycle scope. No GlobalScope.
    val viewModelScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Default
    )

    val viewModel = UserViewModelLike(repository, viewModelScope)

    println("\n--- Cold Flow ---")
    println("Flow created; producer has not started yet.")

    repository.userUpdates().collect { value ->
        println("[Collector] Received: $value")
    }

    println("\n--- StateFlow with stateIn ---")
    println("Initial counterState.value = ${viewModel.counterState.value}")

    val counterCollector = launch {
        viewModel.counterState.collect { value ->
            println("[StateFlow Collector] Counter = $value")
        }
    }

    delay(2_500)
    counterCollector.cancelAndJoin()

    println("\n--- ViewModel-like UI State ---")

    val uiStateCollector = launch {
        viewModel.uiState.collect { state ->
            println(
                "[UI] name=${state.userName}, " +
                    "counter=${state.counter}, " +
                    "loading=${state.isLoading}, " +
                    "error=${state.errorMessage ?: "none"}"
            )
        }
    }

    val observationJob = viewModel.observeUserUpdates()
    delay(1_800)
    observationJob.cancelAndJoin()

    println("\n--- SharedFlow Event ---")

    val collector1 = launch {
        viewModel.events.collect { println("[Collector 1] $it") }
    }
    val collector2 = launch {
        viewModel.events.collect { println("[Collector 2] $it") }
    }

    delay(100)
    viewModel.showError("Unable to refresh user")
    delay(500)

    collector1.cancelAndJoin()
    collector2.cancelAndJoin()
    uiStateCollector.cancelAndJoin()

    // Simulated ViewModel lifecycle ending.
    viewModelScope.cancel()

    println("\n--- Lifecycle Cancellation ---")
    println("ViewModel-like scope cancelled.")
    println("No GlobalScope was used.")

}