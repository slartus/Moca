package ru.slartus.moca.features.`feature-product-info`

import AppFile
import com.benasher44.uuid.uuid4
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.update
import ru.slartus.moca.`core-ui`.base.Action
import ru.slartus.moca.`core-ui`.base.BaseViewModel
import ru.slartus.moca.domain.models.Product
import ru.slartus.moca.domain.models.Torrent
import ru.slartus.moca.domain.repositories.TorrentsSourcesRepository

internal class TorrentsListViewModel(
    scope: CoroutineScope,
    private val repository: TorrentsSourcesRepository
) : BaseViewModel<TorrentsViewState, TorrentsAction, Any>(
    TorrentsViewState(
        isLoading = false,
        data = emptyList()
    )
) {
    private val scope = scope.plus(exceptionHandler + SupervisorJob())
    private var product: Product = Product()
    fun setProduct(product: Product) {
        if (this.product != product) {
            this.product = product
            _stateFlow.update { state ->
                TorrentsViewState(
                    isLoading = state.isLoading,
                    data = emptyList()
                )
            }

            reload()
        }
    }

    private fun reload() {
        _stateFlow.update { state ->
            TorrentsViewState(
                isLoading = true,
                data = state.data
            )
        }

        scope.launch {
            val sources = repository.getSources()
            sources.map { source ->
                launch(SupervisorJob() + exceptionHandler) {
                    val torrents = repository.findIn(source, product)
                    _stateFlow.update { state ->
                        TorrentsViewState(
                            isLoading = state.isLoading,
                            data = state.data + torrents.map { it.map() }
                        )
                    }
                }
            }.joinAll()
            _stateFlow.update { state ->
                TorrentsViewState(
                    isLoading = false,
                    data = state.data
                )
            }
        }

    }

    override fun onError(throwable: Throwable) {
        callAction(
            TorrentsAction.Error(
                throwable.message ?: throwable.toString()
            )
        )
    }

    fun onTorrentClick(torrent: TorrentItem) {
        scope.launch {
            try {
                _stateFlow.update { screenState ->
                    TorrentsViewState(
                        isLoading = screenState.isLoading,
                        data = screenState.data.map { if (torrent.id == it.id) it.copy(isLoading = true) else it }
                    )
                }
                val fileName = torrent.title
                    .replace(Regex("[^\\p{L}\\w]"), "_")
                    .replace(Regex("_+"), "_")
                    .take(32)// why not

                val appFile =
                    AppFile.createTempFile(fileName, ".torrent")
                repository.download(torrent.map(), appFile)
                callAction(TorrentsAction.OpenFile(appFile))
            } finally {
                _stateFlow.update { screenState ->
                    TorrentsViewState(
                        isLoading = screenState.isLoading,
                        data = screenState.data.map { if (torrent.id == it.id) it.copy(isLoading = false) else it }
                    )
                }
            }
        }
    }

    override fun obtainEvent(viewEvent: Any) {

    }
}


internal data class TorrentsViewState(
    val isLoading: Boolean,
    val data: List<TorrentItem>
)

internal sealed class TorrentsAction : Action {
    override val id: String = uuid4().toString()

    class OpenFile(val appFile: AppFile) : TorrentsAction()
    class Error(val message: String) : TorrentsAction()
}

internal data class TorrentItem(
    val id: String = uuid4().toString(),
    val source: String,
    val title: String,
    val url: String,
    val size: String?,
    val seeds: Int?,
    val peers: Int?,
    val downloads: Int? = null,
    val date: String? = null,
    val isLoading: Boolean
)

private fun Torrent.map() = TorrentItem(
    source = this.source,
    title = this.title,
    url = this.url,
    size = this.size,
    seeds = this.seeds,
    peers = this.peers,
    downloads = this.downloads,
    date = this.date,
    isLoading = false
)

private fun TorrentItem.map() = Torrent(
    source = this.source,
    title = this.title,
    url = this.url,
    size = this.size,
    seeds = this.seeds,
    peers = this.peers,
    downloads = this.downloads,
    date = this.date
)