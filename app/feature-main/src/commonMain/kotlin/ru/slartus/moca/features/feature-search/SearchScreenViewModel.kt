package ru.slartus.moca.features.`feature-search`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.slartus.moca.`core-ui`.base.BaseViewModel
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.repositories.SearchRepository
import ru.slartus.moca.features.`feature-product-info`.TorrentsAction
import ru.slartus.moca.features.`feature-product-info`.TorrentsViewState

internal class SearchScreenViewModel<T : Product>(
    private val scope: CoroutineScope,
    private val repository: SearchRepository<T>
) : BaseViewModel<SearchViewState<T>, Action, Any>(
    SearchViewState(
        isLoading = false,
        query = "",
        data = SearchResult<T>(emptyList())
    )
) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable.cause !is CancellationException) {
            onError(throwable)
        }
    }
    private val coroutineContext = exceptionHandler + SupervisorJob()

    private var searchJob: Job = Job()
    fun onQueryChanged(oldQuery: String, query: String) {
        if (oldQuery == query) return
        searchJob.cancel()
        if (query.isEmpty()) {
            _stateFlow.update { state ->
                SearchViewState(
                    isLoading = false,
                    query = query,
                    data = SearchResult(emptyList())
                )
            }
        } else {
            searchJob = scope.launch(coroutineContext) {
                delay(500)

                _stateFlow.update { state ->
                    SearchViewState(
                        isLoading = true,
                        query = state.query,
                        data = state.data
                    )
                }
                val items = repository.search(query)
                if (isActive) {
                    _stateFlow.update { state ->
                        SearchViewState(
                            isLoading = false,
                            query = query,
                            data = SearchResult(items)
                        )
                    }

                }
            }
        }
    }


    private fun onError(exception: Throwable) {
        callAction(
            Action.Error(
                exception.message ?: exception.toString()
            )
        )
    }

    override fun obtainEvent(viewEvent: Any) {

    }
}

internal data class SearchResult<T : Product>(
    val items: List<T>
)

internal data class SearchViewState<T : Product>(
    val isLoading: Boolean,
    val query: String,
    val data: SearchResult<T>
)

internal sealed class Action() : ru.slartus.moca.`core-ui`.base.Action {
    override val id: String = uuid4().toString()

    class Error(val message: String) : Action()
}