package ru.slartus.moca.features.`feature-search`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.repositories.SearchRepository

internal class SearchScreenViewModel<T : Product>(
    private val scope: CoroutineScope,
    private val repository: SearchRepository<T>
) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable.cause !is CancellationException) {
            onError(throwable)
        }
    }
    private val coroutineContext = exceptionHandler + SupervisorJob()
    private val _state = MutableStateFlow(
        SearchViewState(
            isLoading = true,
            data = SearchResult(emptyList()),
            actions = emptyList()
        )
    )

    val stateFlow: StateFlow<SearchViewState> = _state.asStateFlow()


    private var searchJob: Job = Job()
    fun onQueryChanged(query: String) {
        searchJob.cancel()
        if (query.isEmpty()) {
            _state.update { state ->
                SearchViewState(
                    isLoading = false,
                    data = SearchResult(emptyList()),
                    actions = state.actions
                )
            }
        } else {
            searchJob = scope.launch(coroutineContext) {
                delay(300)
                val items = repository.search(query)
                if (isActive) {
                    _state.update { state ->
                        SearchViewState(
                            isLoading = false,
                            data = SearchResult(items),
                            actions = state.actions
                        )
                    }
                }
            }
        }
    }

    private fun onError(exception: Throwable) {
        _state.update { screenState ->
            SearchViewState(
                isLoading = false,
                data = screenState.data,
                actions = screenState.actions + Action.Error(
                    exception.message ?: exception.toString()
                )
            )
        }
    }
}

internal data class SearchResult(
    val items: List<Product>
)

internal data class SearchViewState(
    val isLoading: Boolean,
    val data: SearchResult,
    val actions: List<Action>
)

internal sealed class Action() {
    val id: String = uuid4().toString()

    class Error(val message: String) : Action()
}