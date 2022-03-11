package ru.slartus.moca.features.`feature-product-info`

import AppFile
import com.benasher44.uuid.uuid4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.models.Torrent
import ru.slartus.moca.domain.repositories.TorrentsSourcesRepository

internal class TorrentsListViewModel(
    scope: CoroutineScope,
    private val repository: TorrentsSourcesRepository,
    private val product: Product
) {
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable.cause !is CancellationException) {
            onError(throwable)
        }
    }
    private val scope = scope.plus(exceptionHandler + SupervisorJob())
    private val _state = MutableStateFlow(
        TorrentsViewState(
            isLoading = false,
            data = emptyList(),
            actions = emptyList()
        )
    )

    val stateFlow: StateFlow<TorrentsViewState> = _state.asStateFlow()

    init {
        reload()
    }

    private fun reload() {
        _state.update { state ->
            TorrentsViewState(
                isLoading = true,
                data = state.data,
                actions = state.actions
            )
        }

        scope.launch {
            val sources = repository.getSources()
            sources.map { source ->
                launch(SupervisorJob() + exceptionHandler) {
                    val torrents = repository.findIn(source, product)
                    _state.update { state ->
                        TorrentsViewState(
                            isLoading = state.isLoading,
                            data = state.data + torrents,
                            actions = state.actions
                        )
                    }
                }
            }.joinAll()
            _state.update { state ->
                TorrentsViewState(
                    isLoading = false,
                    data = state.data,
                    actions = state.actions
                )
            }
        }

    }

    fun actionReceived(messageId: String) {
        _state.update { screenState ->
            TorrentsViewState(
                isLoading = false,
                data = screenState.data,
                actions = screenState.actions.filterNot { it.id == messageId },
            )
        }
    }

    private fun onError(exception: Throwable) {
        _state.update { screenState ->
            TorrentsViewState(
                isLoading = false,
                data = screenState.data,
                actions = screenState.actions + TorrentsAction.Error(
                    exception.message ?: exception.toString()
                )
            )
        }
    }

    fun onTorrentClick(torrent: Torrent) {
        scope.launch {
            val appFile = AppFile.createTempFile("tmp_", ".torrent")
            repository.download(torrent, appFile)

            _state.update { screenState ->
                TorrentsViewState(
                    isLoading = screenState.isLoading,
                    data = screenState.data,
                    actions = screenState.actions + TorrentsAction.OpenFile(appFile)
                )
            }
        }
    }
}


internal data class TorrentsViewState(
    val isLoading: Boolean,
    val data: List<Torrent>,
    val actions: List<TorrentsAction>
)

internal sealed class TorrentsAction() {
    val id: String = uuid4().toString()

    class OpenFile(val appFile: AppFile) : TorrentsAction()
    class Error(val message: String) : TorrentsAction()
}