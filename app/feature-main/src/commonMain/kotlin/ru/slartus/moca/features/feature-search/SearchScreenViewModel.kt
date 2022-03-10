package ru.slartus.moca.features.`feature-search`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
            isLoading = false,
            query = "",
            data = SearchResult<T>(emptyList()),
            actions = emptyList()
        )
    )

    val stateFlow: StateFlow<SearchViewState<T>> = _state.asStateFlow()


    private var searchJob: Job = Job()
    fun onQueryChanged(oldQuery: String, query: String) {
        if (oldQuery == query) return
        searchJob.cancel()
        if (query.isEmpty()) {
            _state.update { state ->
                SearchViewState(
                    isLoading = false,
                    query = query,
                    data = SearchResult(emptyList()),
                    actions = state.actions
                )
            }
        } else {
            searchJob = scope.launch(coroutineContext) {
                delay(500)

                _state.update { state ->
                    SearchViewState(
                        isLoading = true,
                        query = state.query,
                        data = state.data,
                        actions = state.actions
                    )
                }
                val items = repository.search(query)
                if (isActive) {
                    _state.update { state ->
                        SearchViewState(
                            isLoading = false,
                            query = query,
                            data = SearchResult(items),
                            actions = state.actions
                        )
                    }

                }
            }
        }
    }

    fun actionReceived(messageId: String) {
        _state.update { screenState ->
            SearchViewState(
                isLoading = false,
                data = screenState.data,
                query = screenState.query,
                actions = screenState.actions.filterNot { it.id == messageId },
            )
        }
    }

    private fun onError(exception: Throwable) {
        _state.update { screenState ->
            SearchViewState(
                isLoading = false,
                data = screenState.data,
                query = screenState.query,
                actions = screenState.actions + Action.Error(
                    exception.message ?: exception.toString()
                )
            )
        }
    }
}

internal data class SearchResult<T : Product>(
    val items: List<T>
)

internal data class SearchViewState<T : Product>(
    val isLoading: Boolean,
    val query: String,
    val data: SearchResult<T>,
    val actions: List<Action>
)

internal sealed class Action() {
    val id: String = uuid4().toString()

    class Error(val message: String) : Action()
}