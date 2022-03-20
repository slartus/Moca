package ru.slartus.moca.features.`feature-settings`

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.slartus.moca.`core-ui`.base.BaseViewModel
import ru.slartus.moca.domain.models.TorrentsSource
import ru.slartus.moca.domain.repositories.TorrentsSourcesRepository

internal class TorrentsSourcesScreenViewModel(
    scope: CoroutineScope,
    private val repository: TorrentsSourcesRepository
) : BaseViewModel<TorrentsSourcesViewState, Action, Any>(
    TorrentsSourcesViewState(
        isLoading = false,
        data = emptyList()
    )
) {
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
            _stateFlow.update { _ ->
                TorrentsSourcesViewState(
                    isLoading = false,
                    data = items
                )
            }
        }
    }

    override fun onError(throwable: Throwable) {
        callAction(
            Action.Error(
                throwable.message ?: throwable.toString()
            )
        )
    }

    fun addTorrentSource(id: Long?, title: String, query: String) {
        scope.launch {
            if (id == null)
                repository.addSource(TorrentsSource(title = title, url = query))
            else
                repository.updateSource(TorrentsSource(id = id, title = title, url = query))

            reload()
        }
    }

    fun onDeleteClick(torrentSource: TorrentsSource) {
        scope.launch {
            repository.deleteSource(torrentSource.id!!)

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