package ru.slartus.moca.features.`feature-settings`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.slartus.moca.domain.models.TorrentsSource
import ru.slartus.moca.domain.repositories.TorrentsSourcesRepository

internal class TorrentsSourcesScreenViewModel(
    scope: CoroutineScope,
    private val repository: TorrentsSourcesRepository
) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable.cause !is CancellationException) {
            onError(throwable)
        }
    }
    private val scope = scope.plus(exceptionHandler + SupervisorJob())
    private val _state = MutableStateFlow(
        TorrentsSourcesViewState(
            isLoading = false,
            data = emptyList(),
            actions = emptyList()
        )
    )

    val stateFlow: StateFlow<TorrentsSourcesViewState> = _state.asStateFlow()

    init {
        reload()
    }

    private fun reload() {
        _state.update { state ->
            TorrentsSourcesViewState(
                isLoading = true,
                data = state.data,
                actions = state.actions
            )
        }

        scope.launch {
            val items = repository.getSources()
            _state.update { state ->
                TorrentsSourcesViewState(
                    isLoading = false,
                    data = items,
                    actions = state.actions
                )
            }
        }
    }

    fun actionReceived(messageId: String) {
        _state.update { screenState ->
            TorrentsSourcesViewState(
                isLoading = false,
                data = screenState.data,
                actions = screenState.actions.filterNot { it.id == messageId },
            )
        }
    }

    private fun onError(exception: Throwable) {
        _state.update { screenState ->
            TorrentsSourcesViewState(
                isLoading = false,
                data = screenState.data,
                actions = screenState.actions + Action.Error(
                    exception.message ?: exception.toString()
                )
            )
        }
    }

    fun addTorrentSource(title: String, query: String) {
        scope.launch {
            repository.addSource(TorrentsSource(title, query))

            reload()
        }
    }

    fun onDeleteClick(torrentSource: TorrentsSource) {
        scope.launch {
            repository.deleteSource(torrentSource)

            reload()
        }
    }
}


internal data class TorrentsSourcesViewState(
    val isLoading: Boolean,
    val data: List<TorrentsSource>,
    val actions: List<Action>
)

internal sealed class Action() {
    val id: String = uuid4().toString()

    class Error(val message: String) : Action()
}