package ru.slartus.moca.features.`feature-settings`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.slartus.moca.`core-ui`.base.BaseViewModel
import ru.slartus.moca.domain.models.TorrentsSource
import ru.slartus.moca.domain.repositories.TorrentsSourcesRepository
import ru.slartus.moca.features.`feature-search`.SearchViewState

internal class TorrentsSourcesScreenViewModel(
    scope: CoroutineScope,
    private val repository: TorrentsSourcesRepository
) : BaseViewModel<TorrentsSourcesViewState, Action, Any>(
    TorrentsSourcesViewState(
        isLoading = false,
        data = emptyList()
    )
) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable.cause !is CancellationException) {
            onError(throwable)
        }
    }
    private val scope = scope.plus(exceptionHandler + SupervisorJob())

    init {
        reload()
    }

    private fun reload() {
        _stateFlow.update { state ->
            TorrentsSourcesViewState(
                isLoading = true,
                data = state.data
            )
        }

        scope.launch {
            val items = repository.getSources()
            _stateFlow.update { state ->
                TorrentsSourcesViewState(
                    isLoading = false,
                    data = items
                )
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

    override fun obtainEvent(viewEvent: Any) {

    }
}


internal data class TorrentsSourcesViewState(
    val isLoading: Boolean,
    val data: List<TorrentsSource>
)

internal sealed class Action() : ru.slartus.moca.`core-ui`.base.Action {
    override val id: String = uuid4().toString()

    class Error(val message: String) : Action()
}